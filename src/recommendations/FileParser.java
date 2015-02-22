package recommendations;

import edu.kit.informatik.FileInputHelper;
import recommendations.nodes.Category;
import recommendations.nodes.Node;
import recommendations.nodes.Product;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class FileParser {

    private static LinkedList<Node> nodes = new LinkedList<Node>();
    private static String[] lines;

    /**
     * Parses a database file
     * @param path Path to file
     * @return List of (maybe connected) Graphs from that File
     */
    public static Graph[] parse(String path) {
        lines = FileInputHelper.read(path);

        for (String line: lines) {
            line = stringCleanup(line);
            parseLine(line.split(" "));

        }
        LinkedList<Node> heads = findHeads();
        Graph[] graphs = new Graph[heads.size()];
        for (int i = 0; i < graphs.length; i++) {
            Graph graph = new Graph(heads.get(i));
            graphs[i] = graph;
        }
        return graphs;
    }

    private static String stringCleanup(String line) {
        String edit = line;
        while (edit.contains("  ")) {
            edit = edit.replace("  ", " ");
        }
        edit = edit.replace("( ", "(");
        edit = edit.replace(" )", ")");
        edit = edit.replace(" =", "=");
        edit = edit.replace("= ", "=");
        while (edit.endsWith(" ")) {
            edit = edit.substring(0, edit.length() - 2);
        }
        while (edit.startsWith(" ")) {
            edit = edit.substring(1);
        }
        return edit;
    }

    private static void parseLine(String[] line) {
        Node node1;
        Node node2;

        if (line[1].startsWith("(")) {
            int id = Integer.parseInt(line[1].substring(4, line[1].length() - 2));
            node1 = new Product(id, line[0]);
        } else {
            node1 = new Category(line[0]);
        }

        if (line[line.length - 1].startsWith("(")) {
            int id = Integer.parseInt(line[line.length - 1].substring(4, line[line.length - 1].length() - 2));
            node2 = new Product(id, line[line.length - 2]);
        } else {
            node2 = new Category(line[line.length - 1]);
        }

        if (node1.equals(node2)) return;

        boolean add1 = true;
        boolean add2 = true;
        Iterator<Node> iter = nodes.iterator();
        while (iter.hasNext()) {
            Node curr = iter.next();
            if (node1.equals(curr)) {
                node1 = curr;
                add1 = false;
            }
            if (node2.equals(curr)) {
                node2 = curr;
                add2 = false;
            }
        }
        if (add1) nodes.add(node1);
        if (add2) nodes.add(node2);

        String command = line[2];
        if (node1.isCategory()) command = line[1];

        if (command.equals("contains")) {
            ((Category) node1).getContains().add(node2);
            node2.getContainedIn().add((Category) node1);
            return;
        }

        if (command.equals("contained-in")) {
            node1.getContainedIn().add((Category) node2);
            ((Category) node2).getContains().add(node1);
            return;
        }

        if (command.equals("part-of")) {
            ((Product) node1).getPartOf().add((Product) node2);
            ((Product) node2).getHasPart().add((Product) node1);
            return;
        }

        if (command.equals("has-part")) {
            ((Product) node2).getPartOf().add((Product) node1);
            ((Product) node1).getHasPart().add((Product) node2);
            return;
        }

        if (command.equals("successor-of")) {
            ((Product) node1).getSuccessorOf().add((Product) node2);
            ((Product) node2).getPredecessorOf().add((Product) node1);
            return;
        }

        if (command.equals("predecessor-of")) {
            ((Product) node2).getSuccessorOf().add((Product) node1);
            ((Product) node1).getPredecessorOf().add((Product) node2);
        }
    }


    private static LinkedList<Node> findHeads() {
        LinkedList<Node> out = new LinkedList<Node>();
        for (Node node: nodes) {
            if (node.isCategory()) {
                if (node.getContainedIn().size() == 0) {
                    out.add(node);
                }
            } else {
                if (node.getContainedIn().size() == 0 && ((Product) node).getPartOf().size() == 0
                        && ((Product) node).getPredecessorOf().size() == 0) {
                    out.add(node);
                }
            }
        }
        return out;
    }
}
