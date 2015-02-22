package recommendations.nodes;

import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public abstract class Node {
    private boolean isCategory;
    private String name;
    private LinkedList<Category> containedIn = new LinkedList<Category>();



    /**
     * returns whether the Node is a category
     * @return TRUE, if Node is category
     */
    public boolean isCategory() {
        return this.isCategory;
    }

    /**
     * Gets the name of the Node
     * @return this.name
     */
    public String getName() {
        return name;
    }



    /**
     * Returns the List of nodes this is contained in
     * @return this.contained in
     */
    public LinkedList<Category> getContainedIn() {
        return containedIn;
    }



    /**
     * Sets the name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets whether this Node is a category
     * @param isCategory true if category
     */
    public void setIsCategory(boolean isCategory) {
        this.isCategory = isCategory;
    }

    @Override
    public boolean equals(Object node2) {
        Node node = (Node) node2;
        if (!this.getName().toLowerCase().equals(node.getName().toLowerCase())) {
            return false;
        }

        if (this.isCategory()) {
            if (!node.isCategory()) return false;
        } else {
            if (node.isCategory()) return false;

            if (((Product) this).getID() != ((Product) node).getID()) {
                return false;
            }
        }
        return true;
    }
}
