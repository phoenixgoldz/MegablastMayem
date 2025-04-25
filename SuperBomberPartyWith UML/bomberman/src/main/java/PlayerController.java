import gameobjects.Player;
import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class PlayerController implements KeyListener {
    private final HashMap<Integer, Key> controls;

    public PlayerController(HashMap<Integer, Key> controls) {
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    private Player player;

    public PlayerController(Player player, HashMap<Integer, Key> controls) {
        this.player = player;
        this.controls = controls;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Key key = this.controls.get(e.getKeyCode());
        if (key != null) {
            key.press();

            if (key == Key.action) {
                Audio.playBombDrop();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Key key = this.controls.get(e.getKeyCode());
        if (key != null) {
            key.release();
        }
    }
}
