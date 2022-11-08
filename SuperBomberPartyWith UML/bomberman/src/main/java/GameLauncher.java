import util.ResourceCollection;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class GameWindow extends JFrame {

    static final int HUD_HEIGHT = 48;   // Size of the HUD. The HUD displays score.
    static final String TITLE = "BomberMan by Trevor Hicks\n and Calvin Bryant";

    GameWindow(GamePanel game) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        Audio.playMenu();// GAME BGM START
    }

    public void update(int fps, int ticks) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.println("[" + dtf.format(time) + "]" + " FPS: " + fps + ", Ticks: " + ticks);
        GameLauncher.window.setTitle(GameWindow.TITLE + " | " + "FPS: " + fps + ", Ticks: " + ticks);
    }

}

class MenuWindow extends JFrame {

    static final int HUD_HEIGHT = 48;   // Size of the HUD. The HUD displays score.
    static final String TITLE = "Super Bomber Party by Trevor Hicks\n and Calvin Bryant";


    MenuWindow(MenuGUI game) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(1020, 1020));

        // game menu selection sounds
        Audio.playMenuMove();

    }

    public void update(int fps, int ticks) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH: mm: ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.println("[" + dtf.format(time) + "]" + " FPS: " + fps + ", Ticks: " + ticks);
        GameLauncher.window.setTitle(GameWindow.TITLE + " | " + "FPS: " + fps + ", Ticks: " + ticks);
    }

}

public class GameLauncher extends Audio {
    //  static gameMenu menu;
    static GameWindow window;
    static String[] args;
    static MenuWindow menuWindow;

    public GameLauncher(int num) {
        super(num);
    }

    public static void main(String[] args) {
        GameLauncher.args = args;
        ResourceCollection.readFiles();
        ResourceCollection.init();
        // MenuGUI

        // menu = new MenuGUI(MenuGUI.drawStartScreen1);
       menuWindow = new MenuWindow(new MenuGUI()) ;
        System.gc();
    }

    public static void startGame() {

        GamePanel game;
        try {
            game = new GamePanel(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e + ": Program args not given");
            game = new GamePanel(null);
        }
        menuWindow.setVisible(false);
        game.init();
        Audio.stopMenu();
        Audio.playGameSong();
        window = new GameWindow(game);

    }

}
