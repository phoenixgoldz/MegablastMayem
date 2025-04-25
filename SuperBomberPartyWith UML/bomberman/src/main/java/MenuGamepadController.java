import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

public class MenuGamepadController implements Runnable {

    private final MenuGUI menu;
    private final ControllerManager manager;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean selectPressed = false;
    private boolean backPressed = false;

    public MenuGamepadController(MenuGUI menu) {
        this.menu = menu;
        this.manager = new ControllerManager();

        System.setProperty("SDL_GAMECONTROLLERCONFIG",
                "030000007e0500000920000000000000,CRKD Controller,platform:Windows,a:b1,b:b0,x:b3,y:b2,back:b8,guide:b12,start:b9,leftstick:b10,rightstick:b11,leftshoulder:b4,rightshoulder:b5,dpup:h0.1,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,leftx:a0,lefty:a1,rightx:a2,righty:a3,lefttrigger:b6,righttrigger:b7");

        manager.initSDLGamepad();
        System.out.println("✅ Gamepad controller initialized");
    }

    @Override
    public void run() {
        while (true) {
            ControllerState state = manager.getState(0);
            if (!state.isConnected) {
                continue;
            }

            if (state.dpadUp && !upPressed) {
                menu.forceFocus();
                menu.navigateUp();
                upPressed = true;
            } else if (!state.dpadUp) {
                upPressed = false;
            }

            if (state.dpadDown && !downPressed) {
                menu.forceFocus();
                menu.navigateDown();
                downPressed = true;
            } else if (!state.dpadDown) {
                downPressed = false;
            }

            if (state.b && !selectPressed) {
                menu.forceFocus();
                menu.selectOption();
                selectPressed = true;
            } else if (!state.b) {
                selectPressed = false;
            }

            if (state.a && !backPressed) {
                menu.forceFocus();
                menu.goBack();
                backPressed = true;
            } else if (!state.a) {
                backPressed = false;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
