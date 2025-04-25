package gameobjects;

import util.GameObjectCollection;
import util.Key;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Bomber extends Player {

    private Bomb bomb;
    private boolean dead;
    private int npcLevel = 0;
    private PassiveAbility passiveAbility = PassiveAbility.NONE;
    public enum PassiveAbility {
        NONE,
        SPEED_BOOST,
        FASTER_BOMB_PLANT,
        RESISTANCE
    }

    // Animation
    private BufferedImage[][] sprites;
    private int direction;  // 0: up, 1: down, 2: left, 3: right
    private int spriteIndex;
    private int spriteTimer;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    // Stats
    private float moveSpeed;
    private int firepower;
    private int maxBombs;
    private int bombAmmo;
    private int bombTimer;
    private boolean pierce;
    private boolean kick;

    public Bomber(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 7, this.position.y + 3, 25, 45);

        this.sprites = spriteMap;
        this.direction = 1;
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        this.moveSpeed = 1;
        this.firepower = 1;
        this.maxBombs = 1;
        this.bombAmmo = this.maxBombs;
        this.bombTimer = 250;
        this.pierce = false;
        this.kick = false;
    }
    public void levelUp() {
        this.npcLevel++;
        this.moveSpeed = Math.min(this.moveSpeed + 0.2f, 4.0f); // Slightly faster, capped at 4
        this.firepower = Math.min(this.firepower + 1, 6); // Slightly more powerful, capped at 6
        System.out.println("🤖 NPC Bomber leveled up! Now Level " + this.npcLevel);
    }

    public boolean isNPC() {
        return this.npcLevel > 0;
    }

    public void setMoveUp(boolean value) {
        movingUp = value;
    }

    public void setMoveDown(boolean value) {
        movingDown = value;
    }

    public void setMoveLeft(boolean value) {
        movingLeft = value;
    }

    public void setMoveRight(boolean value) {
        movingRight = value;
    }

    public void moveUp() {
        this.direction = 0;
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }

    public void moveDown() {
        this.direction = 1;
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }

    public void moveLeft() {
        this.direction = 2;
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }

    public void moveRight() {
        this.direction = 3;
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

    public void plantBomb() {
        float x = Math.round(this.position.getX() / 32) * 32;
        float y = Math.round((this.position.getY() + 16) / 32) * 32;
        Point2D.Float spawnLocation = new Point2D.Float(x, y);

        for (GameObject obj : GameObjectCollection.tileObjects) {
            if (obj.collider.contains(spawnLocation)) return;
        }

        this.bomb = new Bomb(spawnLocation, this.firepower, this.pierce, this.bombTimer, this);
        GameObjectCollection.spawn(bomb);
        this.bombAmmo--;
    }

    public void restoreAmmo() {
        this.bombAmmo = Math.min(this.maxBombs, this.bombAmmo + 1);
    }

    public void addAmmo(int value) {
        System.out.print("Bombs set from " + this.maxBombs);
        this.maxBombs = Math.min(6, this.maxBombs + value);
        this.restoreAmmo();
        System.out.println(" to " + this.maxBombs);
    }

    public void addFirepower(int value) {
        System.out.print("Firepower set from " + this.firepower);
        this.firepower = Math.min(6, this.firepower + value);
        System.out.println(" to " + this.firepower);
    }

    public void addSpeed(float value) {
        System.out.print("Move Speed set from " + this.moveSpeed);
        this.moveSpeed = Math.min(4, this.moveSpeed + value);
        System.out.println(" to " + this.moveSpeed);
    }

    public void setPierce(boolean value) {
        System.out.print("Pierce set from " + this.pierce);
        this.pierce = value;
        System.out.println(" to " + this.pierce);
    }

    public void setKick(boolean value) {
        System.out.print("Kick set from " + this.kick);
        this.kick = value;
        System.out.println(" to " + this.kick);
    }

    public void reduceTimer(int value) {
        System.out.print("Bomb Timer set from " + this.bombTimer);
        this.bombTimer = Math.max(160, this.bombTimer - value);
        System.out.println(" to " + this.bombTimer);
    }

    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }

    public boolean isDead() {
        return this.dead;
    }

    @Override
    public void update() {
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3, this.width - 6, this.height - 16 - 6);

        if (!this.dead) {
            if ((this.spriteTimer += this.moveSpeed) >= 12) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }
            if ((!Key.up.isPressed() && !Key.down.isPressed() && !Key.left.isPressed() && !Key.right.isPressed())
                    || (this.spriteIndex >= this.sprites[0].length)) {
                this.spriteIndex = 0;
            }
            this.sprite = this.sprites[this.direction][this.spriteIndex];
            if (movingUp) moveUp();
            if (movingDown) moveDown();
            if (movingLeft) moveLeft();
            if (movingRight) moveRight();

            if (Key.up.isPressed()) moveUp();
            if (Key.down.isPressed()) moveDown();
            if (Key.left.isPressed()) moveLeft();
            if (Key.right.isPressed()) moveRight();

            if (Key.action.isPressed() && this.bombAmmo > 0) plantBomb();
        } else {
            if (this.spriteTimer++ >= 30) {
                this.spriteIndex++;
                if (this.spriteIndex < this.sprites[4].length) {
                    this.sprite = this.sprites[4][this.spriteIndex];
                    this.spriteTimer = 0;
                } else if (this.spriteTimer >= 250) {
                    this.destroy();
                }
            }
        }
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
        }
    }
    public void assignRandomPassive() {
        PassiveAbility[] abilities = PassiveAbility.values();
        int choice = (int)(Math.random() * abilities.length);
        passiveAbility = abilities[choice];
        System.out.println("🎲 " + this + " assigned " + passiveAbility);
    }

    public Point2D.Float getPosition() {
        return this.position;
    }
    // Somewhere in Bomber.java (below getPosition maybe)
    public int getFirepower() {
        return this.firepower;
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);

        if (intersection.getWidth() >= intersection.getHeight()
                && intersection.getHeight() <= 6
                && Math.abs(this.collider.getCenterX() - collidingObj.collider.getCenterX()) <= 8) {

            if (this.kick && !collidingObj.isKicked()) {
                if (intersection.getMaxY() >= this.collider.getMaxY() && Key.down.isPressed()) {
                    collidingObj.setKicked(true, KickDirection.FromTop);
                }
                if (intersection.getMaxY() >= collidingObj.collider.getMaxY() && Key.up.isPressed()) {
                    collidingObj.setKicked(true, KickDirection.FromBottom);
                }
            }
            this.solidCollision(collidingObj);
        }

        if (intersection.getHeight() >= intersection.getWidth()
                && intersection.getWidth() <= 6
                && Math.abs(this.collider.getCenterY() - collidingObj.collider.getCenterY()) <= 8) {

            if (this.kick && !collidingObj.isKicked()) {
                if (intersection.getMaxX() >= this.collider.getMaxX() && Key.right.isPressed()) {
                    collidingObj.setKicked(true, KickDirection.FromLeft);
                }
                if (intersection.getMaxX() >= collidingObj.collider.getMaxX() && Key.left.isPressed()) {
                    collidingObj.setKicked(true, KickDirection.FromRight);
                }
            }
            this.solidCollision(collidingObj);
        }
    }

    @Override
    public void handleCollision(Powerup collidingObj) {
        collidingObj.grantBonus(this);
        collidingObj.destroy();
    }
}
