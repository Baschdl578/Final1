package recommendations;

import recommendations.nodes.Node;

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

}
