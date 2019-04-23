package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * An extensible default interaction map for collisions caused by the player.
 *
 * The implementation makes use of the interactionmap, and as such can be easily
 * and declaratively extended when new types of units (ghosts, players, ...) are
 * added.
 *
 * @author Arie van Deursen
 * @author Jeroen Roosen
 *
 */
public class DefaultPlayerInteractionMap implements CollisionMap {

    private PointCalculator pointCalculator;

    private final CollisionMap collisions = defaultCollisions();

    /**
     * Create a simple player-based collision map, informing the
     * point calculator about points to be added.
     *
     * @param pointCalculator
     *             Strategy for calculating points.
     */
    public DefaultPlayerInteractionMap(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    @Override
    public void collide(Unit mover, Unit movedInto) {
        collisions.collide(mover, movedInto);
    }

    /**
     * Creates the default collisions Player-Ghost and Player-Pellet.
     *
     * @return The collision map containing collisions for Player-Ghost and
     *         Player-Pellet.
     */
    private CollisionInteractionMap defaultCollisions() {
        CollisionInteractionMap collisionMap = new CollisionInteractionMap();

        collisionMap.onCollision(Player.class, Ghost.class,
            (player, ghost) -> {
                pointCalculator.collidedWithAGhost(player, ghost);
                player.setAlive(false);
                player.setKiller(ghost);
            });

        collisionMap.onCollision(Player.class, Pellet.class,
            (player, pellet) -> {
                pointCalculator.consumedAPellet(player, pellet);
                pellet.leaveSquare();
            });
        return collisionMap;
    }
}
