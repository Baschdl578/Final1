package recommendations.nodes;

import edu.kit.informatik.Terminal;
import recommendations.Main;
import recommendations.errors.Errors;

import java.util.LinkedList;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class Product extends Node {
    private int id;
    private LinkedList<Product> partOf = new LinkedList<Product>();
    private LinkedList<Product> hasPart = new LinkedList<Product>();
    private LinkedList<Product> successorOf = new LinkedList<Product>(); //Using a List here to model product mergers,
                                                                       // where one product has two or more predecessors
    private LinkedList<Product> predecessorOf = new LinkedList<Product>();
                                                                      //List for product splits (e.g. forking a project)

    /**
     * Constructor
     * @param id ID of the Product
     * @param name Name of the Product
     */
    public Product(int id, String name) {
        if (id < 0) {
            Terminal.printLine("Error, " + Main.getErrors().getString("Node.1"));
        }
        this.setName(name);
        this.id = id;
        this.setIsCategory(false);
    }

    /**
     * Returns the ID of a product
     * @return this.id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the List of nodes this is a part of
     * @return this.partOf
     */
    public LinkedList<Product> getPartOf() {
        return partOf;
    }

    /**
     * Returns List of Products this contains
     * @return this.hasPart
     */
    public LinkedList<Product> getHasPart() {
        return hasPart;
    }

    /**
     * Returns a List of direct predecessors
     * @return this.sucessorOf
     */
    public LinkedList<Product> getSuccessorOf() {
        return successorOf;
    }

    /**
     * Returns a List of direct successors
     * @return this.predecessorOf
     */
    public LinkedList<Product> getPredecessorOf() {
        return predecessorOf;
    }
}
