package recommendations;

/**
 * Created by Sebastian on 24.02.2015.
 *
 * Error when searching for a Product in Main.findProduct
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class FinderError extends Exception {
    /**
     * Constructor
     * @param message Error message
     */
    FinderError(String message) {
        super(message);
    }
}