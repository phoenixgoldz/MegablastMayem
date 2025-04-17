import util.ResourceCollection;
import java.io.InputStreamReader;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class GameWindow extends JFrame {
    static final int HUD_HEIGHT = 48;
    static final String TITLE = "MegaBlast Mayhem by Trevor Hicks\nand Calvin Bryant";
    GameWindow(GamePanel game, int width, int height) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();

        Audio.playMenu();
    }

    public void update(int fps, int ticks) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.println("[" + dtf.format(time) + "] FPS: " + fps + ", Ticks: " + ticks);
        GameLauncher.window.setTitle(GameWindow.TITLE + " | FPS: " + fps + ", Ticks: " + ticks);
    }
}


class MenuWindow extends JFrame {

    static final int HUD_HEIGHT = 48;
    static final String TITLE = "MegaBlast Mayhem by Trevor Hicks\nand Calvin Bryant";

    MenuWindow(MenuGUI game) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(720, 720));
        this.setSize(720, 720);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();

        Audio.playMenuMove(); // Menu selection sound
    }

    public void update(int fps, int ticks) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.println("[" + dtf.format(time) + "] FPS: " + fps + ", Ticks: " + ticks);
        GameLauncher.window.setTitle(GameWindow.TITLE + " | FPS: " + fps + ", Ticks: " + ticks);
    }
}

public class GameLauncher extends Audio {

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

        SplashVideoPlayer.playSplashVideo();

        menuWindow = new MenuWindow(new MenuGUI());
        System.gc();
    }
    public static void startGame() {
        // 🛡️ Ensure it's initialized no matter what
        if (ResourceCollection.SpriteMaps.HARD_WALLS.image == null) {
            ResourceCollection.readFiles();
        }
        ResourceCollection.init();

        String[] availableMaps = {
                "/maps/default.csv",
                "/maps/map_corners.csv",
                "/maps/map_cross.csv",
                "/maps/map_spiral.csv"
        };

        String chosenMap = availableMaps[(int) (Math.random() * availableMaps.length)];
        System.out.println("Launching with map: " + chosenMap);

        InputStream stream = GameLauncher.class.getResourceAsStream(chosenMap);
        if (stream == null) {
            System.err.println("Error: Could not load map file: " + chosenMap);
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        GamePanel game = new GamePanel(reader);
        game.init();

// Get dynamic size
        int windowWidth = game.getMapWidth() * 32;
        int windowHeight = (game.getMapHeight() * 32) + GameWindow.HUD_HEIGHT;

        menuWindow.setVisible(false);
        Audio.stopMenu();
        Audio.playGameSong();
        window = new GameWindow(game, windowWidth, windowHeight);

    }
}