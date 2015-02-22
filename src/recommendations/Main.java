package recommendations;

import edu.kit.informatik.Terminal;
import recommendations.errors.Errors;
import recommendations.nodes.Category;
import recommendations.nodes.Node;
import recommendations.nodes.Product;

import java.util.Currency;
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

    private static Node[] recommendationS1(Product reference) {
        LinkedList<Node> nodes = new LinkedList<Node>();

        Node[] parents = new Node[reference.getContainedIn().size()];
        for (Node parent: reference.getContainedIn()) {
            if (parent.isCategory()) {
                for (Node child: ((Category) parent).getContains()) {
                    if (!child.isCategory()) {
                        for (Node element: nodes) {
                            if (!element.equals(child)) nodes.add(child);
                        }
                    }
                }
            }
        }
        Node[] out = new Node[nodes.size()];
        int i = 0;
        Iterator<Node> iter = nodes.iterator();
        while (iter.hasNext()) {
            out[i] = iter.next();
            i++;
        }
        return out;
    }


    private static String printNode(Node node) {
        String out = "\n" + node.getName();
        if (!node.isCategory()) {
            out += ":" + ((Product) node).getID();
        }
        if (node.isCategory()) {
            for (Node child: ((Category) node).getContains()) {
                out += printNode(child);
            }
        }
        return out;
    }

    public static String printGraph() {
        String out = "";
        for (Graph graph: graphs) {
            Node current = graph.getRoot();
            out += current.getName();
            if (!current.isCategory()) {
                out += ":" + ((Product) current).getID();
            } else {
                for (Node child: ((Category) current).getContains()) {
                    out += printNode(child);
                }
            }

        }
        return out;
    }



    /**
     * Main Method
     * @param args Arguments passed by command line
     */
    public static void main(String args[]) {
        if (args.length != 1) {
            Terminal.printLine(errors.getString("File.1"));
            System.exit(0);
        }

        graphs = FileParser.parse(args[0]);

        Terminal.printLine(printGraph());


    }

}
