package recommendations;

import recommendations.nodes.Category;
import recommendations.nodes.Node;

import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class Graph {
    private Node root;

    /**
     * Constructor
     * @param root Root of the graph
     */
    public Graph(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }


    /**
     * Returns a List of all Nodes in the Graph
     * @return LinkedList of all Nodes in the Graph
     */
    public LinkedList<Node> getNodes() {
        return cascadeNodes(this.getRoot());
    }
    private LinkedList<Node> cascadeNodes(Node start) {
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.add(start);
        if (start.isCategory()) {
            for (Node next: ((Category) start).getContains()) {
                nodes.addAll(cascadeNodes(next));
            }
        }
        return nodes;
    }

}
