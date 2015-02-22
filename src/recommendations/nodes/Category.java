package recommendations.nodes;

import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class Category extends Node {
    private LinkedList<Node> contains = new LinkedList<Node>();

    /**
     * Constructor
     * @param name Name of the category
     */
    public Category(String name) {
        this.setName(name);
        this.setIsCategory(true);
    }

    /**
     * Returns the List of contained nodes
     * @return this.contains
     */
    public LinkedList<Node> getContains() {
        return contains;
    }
}
