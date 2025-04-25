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
        this.setPreferredSize(new Dimension(800, 800));
        this.setDoubleBuffered(true);

        Audio.playMenu();
    }

    public int cursor = 1;
    public SubMenu activeMenu = SubMenu.NONE;

    @Override
    public void paintComponent(Graphics g) {
        if (isStarting) {
            drawStartScreen1(g);
            return;
        }

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
    public void forceFocus() {
        if (!this.hasFocus()) {
            this.requestFocusInWindow();
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
    public void navigateDown() {
        cursor = cursor == 3 ? 1 : cursor + 1;
        Audio.playMenuSelect();
        repaint();
    }
    public void navigateUp() {
        cursor = cursor == 1 ? 3 : cursor - 1;
        Audio.playMenuSelect();
        repaint();
    }
    private boolean isStarting = false;

    public void selectOption() {
        if (activeMenu == SubMenu.NONE) {
            switch (cursor) {
                case 1:
                    System.out.println("Start Game triggered");
                    isStarting = true;
                    Audio.playMenuSelect();
                    repaint();

                    // Delay launch in a separate thread to show screen2 first
                    new Thread(() -> {
                        try {
                            Thread.sleep(500); // Let drawStartScreen2 show for 500ms
                        } catch (InterruptedException ignored) {}
                        GameLauncher.startGame();
                        Audio.stopMenu();
                    }).start();
                    return;

                case 2:
                    System.out.println("How to Play selected");
                    activeMenu = SubMenu.HOW_TO_PLAY;
                    break;

                case 3:
                    System.out.println("Controls selected");
                    activeMenu = SubMenu.CONTROLS;
                    break;
            }

            Audio.playMenuSelect();
            repaint();
        }
    }

    public void goBack() {
        if (activeMenu != SubMenu.NONE) {
            activeMenu = SubMenu.NONE;
            Audio.playMenuSelect();
            repaint();
        }
    }

    public static void drawStartScreen1(Graphics g) {
        drawImage(g, "/PlayGameStartScreen1.png");
    }

    public static void drawStartScreen3(Graphics g) {
        drawImage(g, "/HowToPlayStartScreen1.png");
    }

    public static void drawStartScreen4(Graphics g) {
        drawImage(g, "/HowToPlay.png");
    }

    public static void drawStartScreen5(Graphics g) {
        drawImage(g, "/ControlsStartScreen1.png");
    }

    public static void drawStartScreen6(Graphics g) {
        drawImage(g, "/Controls.png");
    }
    public void drawHowToPlay(Graphics g) {
        drawStartScreen4(g); // "/HowToPlay.png"
    }

    public void drawControls(Graphics g) {
        drawStartScreen6(g); // "/Controls.png"
    }

}

