package nl.tudelft.jpacman.points;

import java.net.URLClassLoader;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.logging.Logger;

/**
 * The responsibility of this loader is to obtain
 * the appropriate points calculator.
 * By default the Default calculator is returned.
 */
public class PointCalculatorLoader {

    private static final Logger LOGGER = Logger.getLogger(PointCalculatorLoader.class.getName()); 

    /**
     * Load a points calculator and return it.
     *
     * @return The (dynamically loaded) points calculator.
     */
    public PointCalculator load() {

        try {
            String classname = "Default";
            String path = "file:~/jpacman/src/main/resources/scoreplugins/" 
                        + classname + "PointCalculator.class";

            ClassLoader classloader = new URLClassLoader(new URL[] {new URL(path)});
            Class<?> pointcalc = classloader.loadClass("nl.tudelft.jpacman.points." 
                        + classname + "PointCalculator");

            return (PointCalculator) pointcalc.newInstance();
        } catch (MalformedURLException e) { 
            LOGGER.log(java.util.logging.Level.FINE, 
                "MalformedURLException occured."); 
        } catch (InstantiationException e) {
            LOGGER.log(java.util.logging.Level.FINE, 
                "InstantiationException occured."); 
        } catch (ClassNotFoundException e) {
            LOGGER.log(java.util.logging.Level.FINE, 
                "ClassNotFoundException occured."); 
        } catch (IllegalAccessException e) {
            LOGGER.log(java.util.logging.Level.FINE, 
                "IllegalAccessException occured."); 
        }
        return null;
    }
}
