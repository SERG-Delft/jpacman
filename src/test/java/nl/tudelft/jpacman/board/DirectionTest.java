package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * A very simple (and not particularly useful)
 * test class to have a starting point where to put tests.
 *
 * @author Arie van Deursen
 */
public class DirectionTest {
    /**
     * Do we get the correct delta when moving north?
     */
    @Test
    void testNorth() {
        Direction north = Direction.valueOf("NORTH");
        assertThat(north.getDeltaY()).isEqualTo(-1);
    }

    /**
     * The correct opposite is returned.
     */
    @Test
    void testOpposite() {
        assertThat(Direction.NORTH.opposite()).isEqualTo(Direction.SOUTH);
    }

    /**
     * The opposite of the opposite is the original.
     */
    @Test
    void testOppositeTwice() {
        assertThat(Direction.WEST.opposite().opposite()).isEqualTo(Direction.WEST);
    }
}
