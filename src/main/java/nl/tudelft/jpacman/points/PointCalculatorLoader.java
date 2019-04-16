package nl.tudelft.jpacman.points;

import java.net.MalformedURLException;
import java.util.logging.Logger;
import java.util.Properties;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

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
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("scorecalc.properties"));
            Class<?> pointcalc = loadClass(prop.getProperty("scorecalculator.name"));
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
        } catch (IOException e) {
            LOGGER.log(java.util.logging.Level.FINE, 
                "IOException occured."); 
        }
        return null;
    }

    /**
     * Creates a subclass to help
     * dynamically load .class files.
     * @return Class instance of the read bytestream.
     */
    @SuppressWarnings("checkstyle:methodlength")
    private Class loadClass(String calcName) throws ClassNotFoundException {
        /**
         * Temp subclass extending ClassLoader.
         */
        class CustomClassLoader extends ClassLoader {
            /**
             * @param parent classloader.
             */
            CustomClassLoader(ClassLoader parent) {
                super(parent);
            }
            @Override
            public Class findClass(String fileName) throws ClassNotFoundException {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                        "scoreplugins/" + fileName + "PointCalculator.class");
                byte[] buffer;
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                int nextValue = 0;
                try {
                    while ((nextValue = inputStream.read()) != -1) {
                        byteStream.write(nextValue);
                    }
                } catch (IOException e) {
                    LOGGER.log(java.util.logging.Level.FINE, 
                        "IOException occured."); 
                }
                buffer = byteStream.toByteArray();
                return defineClass("nl.tudelft.jpacman.points." 
                    + fileName + "PointCalculator", buffer, 0, buffer.length);
            }  
        }
        Class<?> pointcalc = new CustomClassLoader(getClass().getClassLoader()).findClass(calcName);
        return pointcalc;
    }
}

