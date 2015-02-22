package recommendations;

import edu.kit.informatik.FileInputHelper;
import recommendations.errors.Errors;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class Main {
    private static Errors errors = new Errors();

    public static Errors getErrors() {
        return errors;
    }

    public static void main (String args[]) {
        FileParser.setLines(FileInputHelper.read(args[0]));
    }

}
