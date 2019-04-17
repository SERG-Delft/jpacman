package nl.tudelft.jpacman.points;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * The responsibility of this loader is to obtain
 * the appropriate points calculator.
 * By default the Default calculator is returned.
 */
public class PointCalculatorLoader {

    private static Class clazz = null;

    /**
     * Load a points calculator and return it.
     *
     * @return The (dynamically loaded) points calculator.
     */
    public PointCalculator load() {

        try {
            if(clazz==null)
                clazz = loadClassFromFile();

            return (PointCalculator) clazz.newInstance();
        } catch (Exception e) {

        }

        return null;
    }

    private Class loadClassFromFile() {

        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("scorecalc.properties"));
            String strategyToLoad = prop.getProperty("scorecalculator.name");

            if(strategyToLoad.equals("DefaultPointCalculator"))
                return DefaultPointCalculator.class;

            return loadClass(strategyToLoad);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not dinamically load the points calculator class. Something is really wrong with your JPacman. Ask a TA.");
            System.exit(-1);
            return null; // dead code
        }
    }

    /**
     * Creates a subclass to help
     * dynamically load .class files.
     * @return Class instance of the read bytestream.
     */
    @SuppressWarnings("checkstyle:methodlength")
    private Class loadClass(String calcName) throws ClassNotFoundException, MalformedURLException {
        URL url = this.getClass().getClassLoader().getResource("scoreplugins/");

        // Convert File to a URL
        URL[] urls = new URL[]{url};

        // Create a new class loader with the directory
        ClassLoader cl = new URLClassLoader(urls, this.getClass().getClassLoader());

        // Load in the class; MyClass.class should be located in
        Class cls = cl.loadClass(calcName);
        return cls;
    }
}

