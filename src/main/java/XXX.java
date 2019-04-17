
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A brand new points calculator, that does more than
 * just adding points.
 */
public class XXX implements PointCalculator {

    private int pelettesEaten;
    private static List<Direction> moves =  new ArrayList<Direction>();
    private static List<Direction> check = new ArrayList<Direction>(
        Arrays.asList(Direction.EAST, Direction.SOUTH, Direction.EAST,
            Direction.SOUTH, Direction.WEST));


    @Override
    public void collidedWithAGhost(Player player, Ghost ghost) {
        // no points for colliding with a ghost
    }

    @Override
    public void foundAPellet(Player player, Pellet pellet) {

        final int maxPeletteScore = 28;
        final int maxPeletteCrash = 34;
        final int deductScore = -15;

        pelettesEaten++;

        // 1. If 28 pelettes eaten and score is higher than 0, deduct 15, else add 10
        if (pelettesEaten >= maxPeletteScore && player.getScore() > 0) {
            player.addPoints(deductScore);
        } else {
            player.addPoints(pellet.getValue());
        }

        // 2. If 35 pelettes eaten and move NORTH, crash.
        // 3. If 35 pelettes eaten, and direction is anything but North, die.
        if (pelettesEaten > maxPeletteCrash && player.getDirection() == Direction.NORTH) {
            System.exit(1);
        } else if (pelettesEaten > maxPeletteCrash) {
            player.setAlive(false);
        }

    }

    @Override
    public void pacmanMoved(Player player, Direction direction) {

        if (moves.isEmpty()) {
            moves.add(direction);
        }
        if (moves.get(moves.size() - 1) != direction) {
            moves.add(direction);
        }

        // 4. If sequence of moves is [E, S, E, S, W], huge increment to score
        if (moves.equals(check)) {
            player.addPoints(Integer.MAX_VALUE);
        }


    }
}
