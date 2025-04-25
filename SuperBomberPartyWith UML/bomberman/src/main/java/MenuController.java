import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class MenuController implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Key key = this.controls.get(e.getKeyCode());

        if (key == Key.up && gui.activeMenu == SubMenu.NONE) {
            gui.navigateUp();
        }

        if (key == Key.down && gui.activeMenu == SubMenu.NONE) {
            gui.navigateDown();
        }

        if (key == Key.action) {
            gui.selectOption(); // show HowToPlay or Controls
        }

        if (key == Key.back) {
            gui.goBack(); // back to start screen
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
    MenuGUI gui;
    private HashMap<Integer, Key> controls;

    public MenuController(MenuGUI gui, HashMap<Integer, Key> controls) {
        this.gui = gui;
        this.controls = controls;
    }


}
