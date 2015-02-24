package recommendations.nodes;

import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public abstract class Node implements Comparable<Node> {
    private boolean isCategory;
    private String name;
    private LinkedList<Category> containedIn = new LinkedList<Category>();

    /**
     * Setter for parents
     * @param containedIn new parents
     */
    public void setContainedIn(LinkedList<Category> containedIn) {
        this.containedIn = containedIn;
    }

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

    /**
     * String representation of a Node
     * @return String representation of a Node
     */
    public String toString() {
        String out = this.getName().toLowerCase();

        if (!this.isCategory()) out += ":" + ((Product) this).getID();

        return out;
    }


    /**
     * Returns the String representation of a Node with all its edges
     * @return String representation of a Node with all its edges
     */
    public String toStringEdges() {
        String out = "";

        for (Node container: this.getContainedIn()) {
            if (!out.endsWith("\n") && !out.equals("")) out += "\n";
            out += this.toString() + "-[contained-in]->" + container.toString();
        }
        if (this.isCategory()) {
            for (Node contains: ((Category) this).getContains()) {
                if (!out.endsWith("\n") && !out.equals("")) out += "\n";
                out += this.toString() + "-[contains]->" + contains.toString();
            }
        } else {
            for (Node part: ((Product) this).getHasPart()) {
                if (!out.endsWith("\n") && !out.equals("")) out += "\n";
                out += this.toString() + "-[has-part]->" + part.toString();
            }
            for (Node collection: ((Product) this).getPartOf()) {
                if (!out.endsWith("\n") && !out.equals("")) out += "\n";
                out += this.toString() + "-[part-of]->" + collection.toString();
            }
            for (Node successor: ((Product) this).getPredecessorOf()) {
                if (!out.endsWith("\n") && !out.equals("")) out += "\n";
                out += this.toString() + "-[predecessor-of]->" + successor.toString();
            }
            for (Node predecessor: ((Product) this).getSuccessorOf()) {
                if (!out.endsWith("\n") && !out.equals("")) out += "\n";
                out += this.toString() + "-[successor-of]->" + predecessor.toString();
            }
        }

        if (out.equals("")) {
            out += this.toString();
        }

        return out;
    }

    /**
     * Returns String representation of this Node for DOT Notation
     * @return String representation of this Node for DOT Notation
     */
    public String toStringDOT() {
        String out = "";
        if (this.isCategory()) {
            out += "\t" + this.getName().toLowerCase() + " [shape=box]\n";
            for (Node contains: ((Category) this).getContains()) {
                out += "\t" + this.getName().toLowerCase() + " -> " + contains.getName().toLowerCase()
                        + " [label=contains]\n";
            }
        } else {
            for (Node part: ((Product) this).getHasPart()) {
                out += "\t" + this.getName().toLowerCase() + " -> " + part.getName().toLowerCase()
                        + " [label=haspart]\n";
            }
            for (Node collection: ((Product) this).getPartOf()) {
                out += "\t" + this.getName().toLowerCase() + " -> " + collection.getName().toLowerCase()
                        + " [label=partof]\n";
            }
            for (Node successor: ((Product) this).getPredecessorOf()) {
                out += "\t" + this.getName().toLowerCase() + " -> " + successor.getName().toLowerCase()
                        + " [label=predecessorof]\n";
            }
            for (Node predecessor: ((Product) this).getSuccessorOf()) {
                out += "\t" + this.getName().toLowerCase() + " -> " + predecessor.getName().toLowerCase()
                        + " [label=successorof]\n";
            }
        }
        for (Node container: this.getContainedIn()) {
            out += "\t" + this.getName().toLowerCase() + " -> " + container.getName().toLowerCase()
                    + " [label=containedin]\n";
        }
        return out;
    }


    /**
     * Compares this Nodes name to that of another.
     * @param to Node to compare to
     * @return A negative integer, zero, or a positive integer
     *          as this Node is less than, equal to, or greater than the specified Node.
     */
    public int compareTo(Node to) {
        return this.getName().toLowerCase().compareTo(to.getName().toLowerCase());
    }
}
