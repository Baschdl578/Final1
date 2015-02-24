package recommendations;

import edu.kit.informatik.Terminal;
import recommendations.errors.Errors;
import recommendations.nodes.Category;
import recommendations.nodes.Node;
import recommendations.nodes.Product;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class Main {
    private static Errors errors = new Errors();
    private static Graph[] graphs;

    /**
     * Returns resource bundle handler
     * @return this.errors
     */
    public static Errors getErrors() {
        return errors;
    }

    /**
     * Recommendation strategy S1
     * @param reference Reference product
     * @return List of recommended products
     */
    public static LinkedList<Product> recommendationS1(Product reference) {
        if (reference.isCategory()) return null;

        LinkedList<Product> nodes = new LinkedList<Product>();

        Node[] parents = new Node[reference.getContainedIn().size()];
        for (Category parent: reference.getContainedIn()) {
            for (Node child: parent.getContains()) {
                if (!child.isCategory()) {
                    if (nodes.isEmpty()) {
                        nodes.add((Product) child);
                    } else {
                        boolean alreadyIn = false;
                        if (child.equals(reference)) alreadyIn = true;
                        for (Product element: nodes) {
                            if (element.equals(child)) alreadyIn = true;
                        }
                        if (!alreadyIn) nodes.add((Product) child);
                    }
                }
            }
        }
        return nodes;
    }


    /**
     * Recommendation strategy S2
     * @param reference Reference product
     * @return List of recommended products
     */
    public static LinkedList<Product> recommendationS2(Product reference) {
        if (reference.isCategory()) return null;

        LinkedList<Product> nodes = new LinkedList<Product>();
        nodes.addAll(getSuccessors(reference));
        return nodes;
    }
    private static LinkedList<Product> getSuccessors(Product prod) {
        LinkedList<Product> out = new LinkedList<Product>();
        for (Product product: prod.getPredecessorOf()) {
            out.add(product);
            out.addAll(getSuccessors(product));
        }
        return out;
    }


    /**
     * Recommendation strategy S3
     * @param reference Reference product
     * @return List of recommended products
     */
    public static LinkedList<Product> recommendationS3(Product reference) {
        if (reference.isCategory()) return null;

        LinkedList<Product> nodes = new LinkedList<Product>();
        nodes.addAll(getPredecessors(reference));
        return nodes;
    }
    private static LinkedList<Product> getPredecessors(Product prod) {
        LinkedList<Product> out = new LinkedList<Product>();
        for (Product product: prod.getSuccessorOf()) {
            out.add(product);
            out.addAll(getPredecessors(product));
        }
        return out;
    }

    /**
     * Prints a recommendation
     * @param products List of recommended products
     */
    public static void printRecommendation(LinkedList<Product> products) {
        String out = "";
        for (Node node: products) {
            out += node.toString() + ",";
        }
        if (!out.equals("")) {
            out = out.substring(0, out.length() - 1);
        }
        Terminal.printLine(out);
    }

    /**
     * Prints all Nodes in all graphs
     */
    public static void printNodes() {
        String out = "";
        for (Node node: getNodes()) {
            out += node.toString() + ",";
        }
        if (!out.equals("")) {
            out = out.substring(0, out.length() - 1);
        }
        Terminal.printLine(out);

    }

    /**
     * Prints the graph(s) in DOT notation
     */
    public static void export() {
        String out = "digraph {\n";
        for (Node node: getNodes()) {
            out += node.toStringDOT();
        }
        if (out.equals("")) {
            out += "\n";
        }
        out += "}";
        Terminal.printLine(out);
    }

    /**
     * FInds a product by ID
     * @param descriptor ID
     * @return Product
     * @throws FinderError If Product could not be found
     */
    public static Product findProduct(String descriptor) throws FinderError {
        int id = 0;
        try {
            id = Integer.parseInt(descriptor);
        } catch (NumberFormatException e) {
            throw new FinderError("Error, " + errors.getString("Main.1"));
        }

        Iterator<Node> iter = getNodes().iterator();
        while (iter.hasNext()) {
            Node current = iter.next();
            if (!current.isCategory()) {
                if (id == ((Product) current).getID()) return (Product) current;
            }
        }
        throw new FinderError("Error, " + errors.getString("Main.2"));
    }

    /**
     * Returns a sorted LinkedList with all Nodes
     * @return sorted LinkedList with all Nodes
     */
    private static LinkedList<Node> getNodes() {
        LinkedList<Node> nodes = new LinkedList<Node>();
        for (Graph graph: graphs) {
            nodes.addAll(graph.getNodes());
        }
        Collections.sort(nodes);

        int i = 0;  //Check for duplicates just to be sure (if several connected graphs, duplicates may appear)
        while (i < nodes.size() - 1) {
            if (nodes.get(i).equals(nodes.get(i + 1))) {
                nodes.remove(i + 1);
            } else i++;
        }
        return nodes;
    }


    /**
     * Prints all Edges in all graphs
     */
    public static void printEdges() {
        String out = "";
        for (Node node: getNodes()) {
            out += node.toStringEdges() + "\n";
        }
        if (!out.equals("")) {
            out = out.substring(0, out.length() - 1);
        }
        Terminal.printLine(out);
    }

    /**
     * Prints basic instructions for the user
     */
    public static void printUsage() {
        Terminal.printLine(errors.getString("Usage.1") + errors.getString("Usage.2") + errors.getString("Usage.3")
                + errors.getString("Usage.4") + errors.getString("Usage.5") + errors.getString("Usage.6")
                + errors.getString("Usage.7") + errors.getString("Usage.8") + errors.getString("Usage.9"));
    }

    /**
     * Main Method
     * @param args Arguments passed by command line
     */
    public static void main(String args[]) {
        if (args.length != 1) {
            Terminal.printLine("Errror, " + errors.getString("File.1"));
            System.exit(0);
        }

        graphs = FileParser.parse(args[0]);
        InputHandler.start();

    }



}


