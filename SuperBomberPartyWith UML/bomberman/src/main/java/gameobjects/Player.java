package gameobjects;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Abstract Player class for user-controlled game objects.
 * Now uses centralized Key input system (via util.Key).
 */
public abstract class Player extends GameObject {

    /**
     * Constructs a Player with the given position and sprite.
     *
     * @param position The initial position of the player.
     * @param sprite   The default sprite image.
     */
    protected Player(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
    }

}
