package nl.tudelft.jpacman.points;

/**
 * The responsibility of this loader is to obtain
 * the appropriate points calculator.
 * By default the Default calculator is returned.
 */
public class PointCalculatorLoader {

    /**
     * Load a points calculator and return it.
     * @return The (dynamically loaded) points calculator.
     */
    public PointCalculator load() {
        // TODO: Azqa, dynamic load here
        return new DefaultPointCalculator();
    }
}
