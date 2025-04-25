import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import gameobjects.Bomber;

public class GamepadController implements Runnable {
    private final ControllerManager manager;
    private final int controllerIndex; // which physical controller
    private final Bomber player; // which Bomber this controls
    private static final float DEADZONE = 0.3f;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean actionPressed = false;

    public GamepadController(int controllerIndex, GamePanel game, int playerIndex) {
        this.controllerIndex = controllerIndex;
        this.manager = new ControllerManager();
        this.manager.initSDLGamepad();

        // Find the correct Bomber player from the HUD
        this.player = game.getHUD().getPlayer(playerIndex);
    }

    @Override
    public void run() {
        while (true) {
            ControllerState state = manager.getState(controllerIndex);
            if (!state.isConnected) continue;

            if (player == null) continue; // Skip if no player found
// Analog stick continuous movement
            player.setMoveUp(state.leftStickY < -DEADZONE);
            player.setMoveDown(state.leftStickY > DEADZONE);
            player.setMoveLeft(state.leftStickX < -DEADZONE);
            player.setMoveRight(state.leftStickX> DEADZONE);

            // Optionally: D-Pad backup
            if (state.dpadUp) player.setMoveUp(true);
            if (state.dpadDown) player.setMoveDown(true);
            if (state.dpadLeft) player.setMoveLeft(true);
            if (state.dpadRight) player.setMoveRight(true);

// Plant bomb (B button)
            if (state.b && !actionPressed) {
                player.plantBomb();
                actionPressed = true;
            } else if (!state.b) {
                actionPressed = false;
            }

            try {
                Thread.sleep(10); // Very responsive input
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
