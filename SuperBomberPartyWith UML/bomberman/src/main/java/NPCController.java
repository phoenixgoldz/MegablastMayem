import gameobjects.Bomber;
import util.GameObjectCollection;

import java.awt.geom.Point2D;

public class NPCController implements Runnable {

    private final Bomber player;
    private static final long MOVE_INTERVAL_MS = 500; // How often to pick a new action
    private boolean escaping = false;
    private long escapeEndTime = 0;
    private static final long ESCAPE_DURATION_MS = 2000; // Escape for 2 seconds after bomb plant

    public NPCController(Bomber player) {
        this.player = player;
    }
    @Override
    public void run() {
        while (true) {
            if (player != null && !player.isDead()) {
                long currentTime = System.currentTimeMillis();

                if (escaping) {
                    // If still in escape mode
                    if (currentTime < escapeEndTime) {
                        escapeRandom();
                    } else {
                        escaping = false; // Escape time over
                    }
                } else {
                    Bomber target = findClosestHuman();
                    if (target != null) {
                        chaseTarget(target);
                    } else {
                        randomMove();
                    }

                    // Randomly plant bomb near players
                    if (Math.random() < 0.2 && player.getFirepower() > 0) {
                        player.plantBomb();
                        triggerEscape(); // ⬅️ After bomb plant, start escaping!
                    }
                }
            }

            try {
                Thread.sleep(MOVE_INTERVAL_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void triggerEscape() {
        escaping = true;
        escapeEndTime = System.currentTimeMillis() + ESCAPE_DURATION_MS;
    }
    private void escapeRandom() {
        player.setMoveUp(false);
        player.setMoveDown(false);
        player.setMoveLeft(false);
        player.setMoveRight(false);

        double random = Math.random();
        if (random < 0.25) {
            player.setMoveDown(true); // Move away (opposite)
        } else if (random < 0.5) {
            player.setMoveRight(true);
        } else if (random < 0.75) {
            player.setMoveLeft(true);
        } else {
            player.setMoveUp(true);
        }
    }

    private Bomber findClosestHuman() {
        Bomber closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Bomber other : GameObjectCollection.bomberObjects) {
            if (other == player) continue;
            if (other.isDead()) continue;

            double distance = player.getPosition().distance(other.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                closest = other;
            }
        }

        return closest;
    }

    private void chaseTarget(Bomber target) {
        Point2D.Float myPos = player.getPosition();
        Point2D.Float targetPos = target.getPosition();

        // Reset previous movement
        player.setMoveUp(false);
        player.setMoveDown(false);
        player.setMoveLeft(false);
        player.setMoveRight(false);

        // Prioritize vertical or horizontal movement randomly
        if (Math.random() < 0.5) {
            if (targetPos.y < myPos.y) {
                player.setMoveUp(true);
            } else if (targetPos.y > myPos.y) {
                player.setMoveDown(true);
            } else if (targetPos.x < myPos.x) {
                player.setMoveLeft(true);
            } else {
                player.setMoveRight(true);
            }
        } else {
            if (targetPos.x < myPos.x) {
                player.setMoveLeft(true);
            } else if (targetPos.x > myPos.x) {
                player.setMoveRight(true);
            } else if (targetPos.y < myPos.y) {
                player.setMoveUp(true);
            } else {
                player.setMoveDown(true);
            }
        }
    }

    private void randomMove() {
        player.setMoveUp(false);
        player.setMoveDown(false);
        player.setMoveLeft(false);
        player.setMoveRight(false);

        double random = Math.random();
        if (random < 0.25) {
            player.setMoveUp(true);
        } else if (random < 0.5) {
            player.setMoveDown(true);
        } else if (random < 0.75) {
            player.setMoveLeft(true);
        } else {
            player.setMoveRight(true);
        }
    }
}
