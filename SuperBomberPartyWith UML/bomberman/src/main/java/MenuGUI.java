import util.Key;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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
        //BGM music for Menu
        Audio.playMenu();
    }

    //background 1 - Play Game 1
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
        }else if (activeMenu == SubMenu.HOW_TO_PLAY){
            drawHowToPlay(g);
        }else if (activeMenu == SubMenu.CONTROLS){
            drawControls(g);
        }
    }

    public static void drawStartScreen1(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGameStartScreen1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 2 - Play Game 2
    public static void drawStartScreen2(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGameStartScreen2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 3 - How to Play 1
    public static void drawStartScreen3(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/HowToPlayStartScreen1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 4 - How to Play 2
    public static void drawStartScreen4(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/HowToPlayStartScreen2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 5 - Controls 1
    public static void drawStartScreen5(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/ControlsStartScreen1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 6 - Controls 2
    public static void drawStartScreen6(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/ControlsStartScreen2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 7 - 2 Player 1
    public static void drawPlayScreen1(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGame2PlayerScreen1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 8 - 2 player 2
    public static void drawPlayScreen2(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGame2PlayerScreen2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 9 - 3 Player 1
    public static void drawPlayScreen3(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGame3PlayerScreen1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 10 - 3 Player 2
    public static void drawPlayScreen4(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGame3PlayerScreen2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 11 - 4 Player 1
    public static void drawPlayScreen5(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGame4PlayerScreen1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 12 - 4 Player 2
    public static void drawPlayScreen6(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/PlayGame4PlayerScreen2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 13
    public static void drawHowToPlay(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/HowToPlay.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }

    //background 14
    public static void drawControls(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/Controls.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.drawImage(img, 0, 0, 720, 720, null);
    }
}
