package gameobjects;

import ChaosMutation.ChaosMutation;
import util.GameObjectCollection;
import util.ResourceCollection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static ChaosMutation.ChaosMutation.*;

/**
 * Bomb objects that are created by bombers.
 */
public class Bomb extends TileObject {

    // Original bomber that placed this bomb
    private Bomber bomber;
    private float pulseScale = 1.0f;
    private boolean growing = true;
    private int bombWidth;
    private int bombHeight;

    // Animation
    private BufferedImage[][] sprites;
    private int spriteIndex;
    private int spriteTimer;

    // Stats
    private int firepower;
    private boolean pierce;
    private int timeToDetonate;
    private int timeElapsed;

    // Kicking bomb
    private boolean kicked;
    private KickDirection kickDirection;

    /**
     * Constructs a bomb object with values passed in by a bomber object.
     * @param position Coordinates of this object in the game world
     * @param firepower Strength of the bomb explosionContact
     * @param pierce Whether or not the explosions will pierce soft walls
     * @param timer How long before the bomb detonates
     * @param bomber Original bomber that placed this bomb
     */
    public Bomb(Point2D.Float position, int firepower, boolean pierce, int timer, Bomber bomber) {
        super(position, pierce ? ResourceCollection.SpriteMaps.BOMB_PIERCE.getSprites()[0][0] : ResourceCollection.SpriteMaps.BOMB.getSprites()[0][0]);

        this.width = 39;   // 🎯 UPDATED size
        this.height = 16;  // 🎯 UPDATED size
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        this.sprites = pierce ? ResourceCollection.SpriteMaps.BOMB_PIERCE.getSprites() : ResourceCollection.SpriteMaps.BOMB.getSprites();
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        this.firepower = firepower;
        this.pierce = pierce;
        this.timeToDetonate = timer;
        this.bomber = bomber;
        this.timeElapsed = 0;
        this.breakable = true;
        this.kicked = false;
        this.kickDirection = KickDirection.Nothing;
    }

    private ChaosMutation chaosMutation = ChaosMutation.NONE;
    public void setChaosMutation(ChaosMutation mutation) {
        this.chaosMutation = mutation;
    }

    /**
     * Bomb detonates upon destroy and creates explosions. Also replenishes ammo for original bomber.
     */
    private void explode() {
        this.snapToGrid();

        switch (this.chaosMutation) {
            case GRAVITY:
                spawnGravityExplosion();
                break;
            case CHAIN:
                spawnChainExplosion();
                break;
            case MEGABLAST:
                spawnMegaBlastExplosion();
                break;
            case GHOST:
                spawnGhostExplosion();
                break;
            default:
                spawnNormalExplosion();
                break;
        }

        this.bomber.restoreAmmo();
    }


    public void setKicked(boolean kicked, KickDirection kickDirection) {
        this.kicked = kicked;
        this.kickDirection = kickDirection;
    }

    public boolean isKicked() {
        return this.kicked;
    }

    public void stopKick() {
        this.kicked = false;
        this.kickDirection = KickDirection.Nothing;
        this.snapToGrid();
    }

    /**
     * Controls animation and detonation timer.
     */
    @Override
    public void update() {
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        // Animate sprite
        int framesLeft = timeToDetonate - timeElapsed;
// Handle pulsing size
        if (growing) {
            pulseScale += 0.005f; // Grow slowly
            if (pulseScale >= 1.08f) { // Max scale (8% bigger)
                growing = false;
            }
        } else {
            pulseScale -= 0.005f; // Shrink slowly
            if (pulseScale <= 0.92f) { // Min scale (8% smaller)
                growing = true;
            }
        }

        if (framesLeft < 60) { // fast flash near explosion
            if (this.spriteTimer++ >= 2) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }
        } else {
            if (this.spriteTimer++ >= 4) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }
        }

        if (this.spriteIndex >= this.sprites[0].length) {
            this.spriteIndex = 0;
        }
        this.sprite = this.sprites[0][this.spriteIndex];

        // Detonate after timeToDetonate
        if (this.timeElapsed++ >= this.timeToDetonate) {
            this.destroy();
        }

        // Move if kicked
        if (this.kicked) {
            this.position.setLocation(this.position.x + this.kickDirection.getVelocity().x,
                    this.position.y + this.kickDirection.getVelocity().y);
        }

    }
    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Determine glow color based on chaos mutation
        Color glowColor = Color.WHITE;
        switch (this.chaosMutation) {
            case GRAVITY:
                glowColor = Color.CYAN;
                break;
            case CHAIN:
                glowColor = Color.YELLOW;
                break;
            case MEGABLAST:
                glowColor = Color.RED;
                break;
            case GHOST:
                glowColor = Color.LIGHT_GRAY;
                break;
            default:
                glowColor = Color.WHITE;
                break;
        }

        // Save old settings
        Composite oldComposite = g2d.getComposite();
        AffineTransform oldTransform = g2d.getTransform();
        Object oldHint = g2d.getRenderingHint(RenderingHints.KEY_INTERPOLATION);

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Draw the glow (slightly bigger)
        AffineTransform glowTransform = new AffineTransform();
        glowTransform.translate(this.position.x + this.width / 2, this.position.y + this.height / 2);
        glowTransform.scale(this.pulseScale * 1.2, this.pulseScale * 1.2);
        glowTransform.translate(-this.width / 2, -this.height / 2);

        g2d.setTransform(glowTransform);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f)); // semi-transparent
        g2d.setColor(glowColor);
        g2d.fillRect(0, 0, (int)this.width, (int)this.height); // ✅ FIXED

        // Restore settings
        g2d.setComposite(oldComposite);
        g2d.setTransform(oldTransform);

        // Now draw the real sprite
        AffineTransform bombTransform = new AffineTransform();
        bombTransform.translate(this.position.x + this.width / 2, this.position.y + this.height / 2);
        bombTransform.scale(this.pulseScale, this.pulseScale);
        bombTransform.translate(-this.width / 2, -this.height / 2);

        g2d.setTransform(bombTransform);
        g2d.drawImage(this.sprite, 0, 0, null);

        if (oldHint != null) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oldHint);
        }
    }

    @Override
    public void onDestroy() {
        this.explode();
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {
        Point2D.Float temp = new Point2D.Float((float) this.collider.getCenterX() + this.kickDirection.getVelocity().x, (float) this.collider.getCenterY() + this.kickDirection.getVelocity().y);
        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
        if (this.kicked && intersection.contains(temp)) {
            System.out.println("Stop kick called");
            this.stopKick();
            this.solidCollision(collidingObj);
            this.snapToGrid();
        }
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
        this.stopKick();
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.solidCollision(collidingObj);
        this.stopKick();
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }


    private void spawnNormalExplosion() {
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));
    }
    private void spawnGravityExplosion() {
        // Normal explosions first
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));

        // 🌌 Gravity Pull Effect
        for (GameObject obj : GameObjectCollection.playerObjects) {
            if (obj instanceof Bomber) {
                Bomber bomber = (Bomber) obj;

                // Calculate distance from bomb center to player center
                float dx = (float)(this.position.x + this.width / 2) - (float)(bomber.position.x + bomber.width / 2);
                float dy = (float)(this.position.y + this.height / 2) - (float)(bomber.position.y + bomber.height / 2);
                float distance = (float)Math.sqrt(dx * dx + dy * dy);

                if (distance < 160) { // Only affect players within 160px range
                    float strength = (160 - distance) / 160; // Stronger when closer
                    float pullForce = strength * 4.0f; // Max pull speed

                    // Normalize direction
                    if (distance != 0) {
                        dx /= distance;
                        dy /= distance;
                    }

                    // Apply pull
                    bomber.position.setLocation(
                            bomber.position.x + dx * pullForce,
                            bomber.position.y + dy * pullForce
                    );
                }
            }
        }

        System.out.println("🌌 Gravity Bomb activated with pull!");
    }


    private void spawnChainExplosion() {
        // Regular explosion first
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));
        // Then spawn a second bomb in random direction
        spawnChainReactionBomb();
        System.out.println("⛓ Chain Bomb triggered!");
    }

    private void spawnChainReactionBomb() {
        Point2D.Float chainPos = new Point2D.Float(this.position.x + (float)(Math.random() * 64 - 32), this.position.y + (float)(Math.random() * 64 - 32));
        Bomb chainBomb = new Bomb(chainPos, 2, this.pierce, 120, this.bomber); // small quick bomb
        GameObjectCollection.spawn(chainBomb);
    }

    private void spawnMegaBlastExplosion() {
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower * 2, true)); // Double firepower, always pierce
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower * 2, true));
        System.out.println("💥 MEGABLAST Explosion unleashed!");
    }

    private void spawnGhostExplosion() {
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));
        // TODO: In Ghost Mode, explosions could be invisible except on hit (cool twist if you want)
        System.out.println("👻 Ghost Bomb exploded silently!");
    }

/**
 * Provides the speed for bomb moving from kick. Speed should be 6 to ensure the kicking logic is as smooth
 * as possible. Changing the value is dangerous and can introduce bugs to the kicking logic.
 */
enum KickDirection {

    FromTop(new Point2D.Float(0, 6)),
    FromBottom(new Point2D.Float(0, -6)),
    FromLeft(new Point2D.Float(6, 0)),
    FromRight(new Point2D.Float(-6, 0)),
    Nothing(new Point2D.Float(0, 0));

    private Point2D.Float velocity;

    KickDirection(Point2D.Float velocity) {
        this.velocity = velocity;
    }

    public Point2D.Float getVelocity() {
        return this.velocity;
    }

}
}
