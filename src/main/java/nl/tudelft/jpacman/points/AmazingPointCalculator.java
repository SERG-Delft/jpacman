package nl.tudelft.jpacman.points;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;

import java.util.ArrayList;
import java.util.Collections; 
import java.util.Arrays;

/**
 * A brand new points calculator, that does more than
 * just adding points
 */
public class AmazingPointCalculator implements PointCalculator {

    private int pelettesEaten;
    private static ArrayList<Direction> moves =  new ArrayList<Direction>();
    private static ArrayList<Direction> check = new ArrayList<Direction> (Arrays.asList(Direction.EAST, Direction.SOUTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    
    @Override
    public void collidedWithAGhost(Player player, Ghost ghost) {
        // no points for colliding with a ghost
    }

    @Override
    public void consumedAPellet(Player player, Pellet pellet) {
        pelettesEaten ++;
        
        // 1. If 28 pelettes eaten and score is higher than 0, deduct 15, else add 10
        if (pelettesEaten >= 28 && player.getScore() > 0)
            player.addPoints(-15);
        else
            player.addPoints(pellet.getValue());

        // 2. If 35 pelettes eaten and move NORTH, crash.
        // 3. If 35 pelettes eaten, and direction is anything but North, die.
        if (pelettesEaten > 34 && player.getDirection() == Direction.NORTH)
           System.exit(123); 
        else if (pelettesEaten > 34)
           player.setAlive(false); 

    }

    @Override
    public void pacmanMoved(Player player, Direction direction) {

        if (moves.size() == 0)
            moves.add(direction);
        if (moves.get(moves.size() - 1) != direction)
            moves.add(direction);

        // 4. If sequence of moves is [E, S, E, S, W], huge increment to score
        if (moves.equals(check)) 
            player.addPoints(Integer.MAX_VALUE);


    }
}
