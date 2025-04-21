import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import gameobjects.Player;

public class GamepadController implements Runnable {
    private final ControllerManager manager;
    private final Player player;
    private final int index;

    public GamepadController(Player player, int index) {
        this.player = player;
        this.index = index;
        this.manager = new ControllerManager();
        manager.initSDLGamepad();
    }

    @Override
    public void run() {
        while (true) {
            ControllerState state = manager.getState(index);
            if (!state.isConnected) continue;

            if (state.dpadUp) player.toggleUpPressed();
            else player.unToggleUpPressed();

            if (state.dpadDown) player.toggleDownPressed();
            else player.unToggleDownPressed();

            if (state.dpadLeft) player.toggleLeftPressed();
            else player.unToggleLeftPressed();

            if (state.dpadRight) player.toggleRightPressed();
            else player.unToggleRightPressed();

            if (state.a) player.toggleActionPressed();
            else player.unToggleActionPressed();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
