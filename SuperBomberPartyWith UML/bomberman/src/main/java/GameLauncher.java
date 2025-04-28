import com.studiohartman.jamepad.ControllerManager;
import com.sun.glass.events.KeyEvent;
import gameobjects.Bomber;
import util.Key;
import util.ResourceCollection;
import java.io.InputStreamReader;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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

        // Setup gamepad-aware menu
        MenuGUI gui = new MenuGUI();
        MenuGamepadController menuPad = new MenuGamepadController(gui);
        Thread menuPadThread = new Thread(menuPad);
        System.out.println("[DEBUG] Gamepad thread started");

        menuPadThread.start();

        // ❌ REMOVE this line:
        // GamepadController pad = new GamepadController(0);
        // new Thread(pad).start();

        menuWindow = new MenuWindow(gui);

        System.gc();
    }

    public static void startGame() {
        // Always load resources first
        ResourceCollection.readFiles();
        ResourceCollection.init();

        // Pick map
        String[] availableMaps = {
                "/maps/default.csv",
                "/maps/map_border.csv",
                "/maps/map_checkerboard.csv",
                "/maps/map_fortress.csv",
                "/maps/map_spiral.csv",
                "/maps/map_spiral_v2.csv",
                "/maps/map_x_pattern.csv"
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

        // 🧍 Set up players based on parsed map (generateMap handles it)
        game.init();
// After game.init();
        ControllerManager manager = new ControllerManager();
        manager.initSDLGamepad();

        for (int i = 0; i < 4; i++) {
            Bomber player = game.getHUD().getPlayer(i);
            if (i == 0) {
                // Player 1 is ALWAYS keyboard controlled
                System.out.println("🧑‍💻 Player 1 using Keyboard.");
                // Keyboard already handled by PlayerController added during map parsing
            } else {
                if (manager.getState(i).isConnected) {
                    System.out.println("🎮 Controller " + (i + 1) + " connected!");
                    new Thread(new GamepadController(i, game, i)).start();
                } else {
                    System.out.println("🤖 Spawning NPC for Bomber " + (i + 1));
                    if (player != null) {
                        new Thread(new NPCController(player)).start();
                    }
                }
            }
        }

        // Finalize UI
        int windowWidth = game.getMapWidth() * 32;
        int windowHeight = (game.getMapHeight() * 32) + GameWindow.HUD_HEIGHT;

        menuWindow.setVisible(false);
        Audio.stopMenu();
        Audio.playGameSong();
        window = new GameWindow(game, windowWidth, windowHeight);
    }
}
