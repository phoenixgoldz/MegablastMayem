import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

public class Main {

    public static void main(String[] args) {
        GameLauncher.args = args;

        System.setProperty("SDL_GAMECONTROLLERCONFIG",
                "030000007e0500000920000000000000,CRKD Controller,platform:Windows,a:b1,b:b0,x:b3,y:b2,back:b8,guide:b12,start:b9,leftstick:b10,rightstick:b11,leftshoulder:b4,rightshoulder:b5,dpup:h0.1,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,leftx:a0,lefty:a1,rightx:a2,righty:a3,lefttrigger:b6,righttrigger:b7");

        // Play splash video first
        SplashVideoPlayer.playSplashVideo();

        // Set up menu and menu gamepad controller
        MenuGUI gui = new MenuGUI();
        MenuGamepadController menuPad = new MenuGamepadController(gui);
        new Thread(menuPad).start();

        // Corrected this line!
        GameLauncher.menuWindow = new MenuWindow(gui);

        // (Optional: Debugging, you can still print controller state)
        ControllerManager manager = new ControllerManager();
        manager.initSDLGamepad();
        while (true) {
            ControllerState state = manager.getState(0);
            System.out.println("Connected: " + state.isConnected);
            System.out.println("A: " + state.a + ", B: " + state.b);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }
}
