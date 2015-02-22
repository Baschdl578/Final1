package recommendations.errors;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Sebastian on 22.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class Errors {
    private ResourceBundle resourceBundle;

    /**
     * Constructor
     */
    public Errors() {
        Locale local = new Locale(new Properties().getProperty("local"));
        try {
            resourceBundle = ResourceBundle.getBundle("recommendations.errors.messages", local);
        } catch (MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle("recommendations.errors.messages", Locale.ENGLISH);
        }

    }
    /**
     * @param key
     * the message key
     * @return returns the message of the specified language
     */
    public String getString(String key)
    {
        try
        {
            return resourceBundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            e.printStackTrace();
            return '!' + key + '!';
        }
    }
}