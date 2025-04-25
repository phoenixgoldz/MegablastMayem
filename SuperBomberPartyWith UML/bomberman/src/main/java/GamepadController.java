import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class GamepadController implements Runnable {
    private final ControllerManager manager;
    private final int index;
    private Robot robot;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean aPressed = false;
    private boolean bPressed = false;

    public GamepadController(int index) {
        this.index = index;
        this.manager = new ControllerManager();
        this.manager.initSDLGamepad();
        try {
            this.robot = new Robot(); // simulate key events
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    @Override
    public void run() {
        while (true) {
            ControllerState state = manager.getState(index);
            if (!state.isConnected) continue;

            // 🔼 UP
            if (state.dpadUp && !upPressed) {
                pressKey(KeyEvent.VK_UP);
                upPressed = true;
            } else if (!state.dpadUp) {
                upPressed = false;
            }

            // 🔽 DOWN
            if (state.dpadDown && !downPressed) {
                pressKey(KeyEvent.VK_DOWN);
                downPressed = true;
            } else if (!state.dpadDown) {
                downPressed = false;
            }

            // ✅ SELECT (B on Switch Pro = b0)
            if (state.b && !bPressed) {
                pressKey(KeyEvent.VK_ENTER);
                bPressed = true;
            } else if (!state.b) {
                bPressed = false;
            }

            // 🔙 BACK (A on Switch Pro = b1)
            if (state.a && !aPressed) {
                pressKey(KeyEvent.VK_BACK_SPACE);
                aPressed = true;
            } else if (!state.a) {
                aPressed = false;
            }

            try {
                Thread.sleep(100); // smooth responsiveness
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
