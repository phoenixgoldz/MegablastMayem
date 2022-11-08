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
      //  System.out.println("im working");

        if (this.controls.get(e.getKeyCode()) == Key.up && this.gui.activeMenu == SubMenu.NONE) {
            if (this.gui.cursor == 1) {
                this.gui.cursor = 3;
            } else
                this.gui.cursor--;
          //  System.out.println("up was pressed");
            Audio.playMenuSelect();
            this.gui.repaint();
        }
        if (this.controls.get(e.getKeyCode()) == Key.down && this.gui.activeMenu == SubMenu.NONE) {
            if (this.gui.cursor == 3) {
                this.gui.cursor = 1;
            } else
                this.gui.cursor++;
          //  System.out.println("down was pressed");
            Audio.playMenuSelect();
            this.gui.repaint();
        }

        if (this.controls.get(e.getKeyCode()) == Key.action && this.gui.activeMenu == SubMenu.NONE) {
            if (this.gui.cursor == 1) {
                GameLauncher.startGame();
                Audio.stopMenu();
            }else if (this.gui.cursor == 2) {
                this.gui.activeMenu = SubMenu.HOW_TO_PLAY;
            }else
                if (this.gui.cursor == 3) {
                this.gui.activeMenu = SubMenu.CONTROLS;
            }
         //   System.out.println("enter was pressed");
            Audio.playMenuSelect();
            this.gui.repaint();
        }
        if (this.controls.get(e.getKeyCode()) == Key.back) {
            this.gui.activeMenu = SubMenu.NONE;
            Audio.playMenuSelect();
            this.gui.repaint();
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
