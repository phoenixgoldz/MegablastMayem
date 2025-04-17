import util.Key;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class MenuGUI extends JPanel {

    private final HashMap<Integer, Key> controls1;

    public MenuGUI() {
        this.controls1 = new HashMap<>();
        this.controls1.put(KeyEvent.VK_UP, Key.up);
        this.controls1.put(KeyEvent.VK_DOWN, Key.down);
        this.controls1.put(KeyEvent.VK_ENTER, Key.action);
        this.controls1.put(KeyEvent.VK_BACK_SPACE, Key.back);

        MenuController menuController = new MenuController(this, this.controls1);
        this.addKeyListener(menuController);
        this.setFocusable(true);
        this.requestFocus();
        this.setPreferredSize(new Dimension(720, 720));
        Audio.playMenu();
    }

    public int cursor = 1;
    public SubMenu activeMenu = SubMenu.NONE;

    @Override
    public void paintComponent(Graphics g) {
        if (activeMenu == SubMenu.NONE) {
            switch (cursor) {
                case 1:
                    drawStartScreen1(g);
                    break;
                case 2:
                    drawStartScreen3(g);
                    break;
                case 3:
                    drawStartScreen5(g);
                    break;
            }
        } else if (activeMenu == SubMenu.HOW_TO_PLAY) {
            drawHowToPlay(g);
        } else if (activeMenu == SubMenu.CONTROLS) {
            drawControls(g);
        }
    }

    private static void drawImage(Graphics g, String path) {
        try {
            BufferedImage img = ImageIO.read(MenuGUI.class.getResource(path));
            g.drawImage(img, 0, 0, 720, 720, null);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public static void drawStartScreen1(Graphics g) {
        drawImage(g, "/resources/PlayGameStartScreen1.png");
    }

    public static void drawStartScreen2(Graphics g) {
        drawImage(g, "/resources/PlayGameStartScreen2.png");
    }

    public static void drawStartScreen3(Graphics g) {
        drawImage(g, "/resources/HowToPlayStartScreen1.png");
    }

    public static void drawStartScreen4(Graphics g) {
        drawImage(g, "/resources/HowToPlayStartScreen2.png");
    }

    public static void drawStartScreen5(Graphics g) {
        drawImage(g, "/resources/ControlsStartScreen1.png");
    }

    public static void drawStartScreen6(Graphics g) {
        drawImage(g, "/resources/ControlsStartScreen2.png");
    }

    public static void drawPlayScreen1(Graphics g) {
        drawImage(g, "/resources/PlayGame2PlayerScreen1.png");
    }

    public static void drawPlayScreen2(Graphics g) {
        drawImage(g, "/resources/PlayGame2PlayerScreen2.png");
    }

    public static void drawPlayScreen3(Graphics g) {
        drawImage(g, "/resources/PlayGame3PlayerScreen1.png");
    }

    public static void drawPlayScreen4(Graphics g) {
        drawImage(g, "/resources/PlayGame3PlayerScreen2.png");
    }

    public static void drawPlayScreen5(Graphics g) {
        drawImage(g, "/resources/PlayGame4PlayerScreen1.png");
    }

    public static void drawPlayScreen6(Graphics g) {
        drawImage(g, "/resources/PlayGame4PlayerScreen2.png");
    }

    public static void drawHowToPlay(Graphics g) {
        drawImage(g, "/resources/HowToPlay.png");
    }

    public static void drawControls(Graphics g) {
        drawImage(g, "/resources/Controls.png");
    }
}
