package nl.tudelft.jpacman.points;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;

public interface PointCalculator {

    void collidedWithAGhost(Player player, Ghost ghost);
    void foundAPellet(Player player, Pellet pellet);
    void pacmanMoved(Player player, Direction direction);

}
