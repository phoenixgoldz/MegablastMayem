import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import java.io.File;

public class SplashVideoPlayer extends Application {

    private static final String VIDEO_PATH = "videos/SplashVideo.mp4";
    @Override
    public void start(Stage stage) {
        String resource = getClass().getResource(VIDEO_PATH).toExternalForm();
        Media media = new Media(resource);
        MediaPlayer player = new MediaPlayer(media);
        MediaView viewer = new MediaView(player);

        // Set the view size to fit 800x600
        viewer.setPreserveRatio(true);
        viewer.setFitWidth(800);
        viewer.setFitHeight(720);

        player.setOnEndOfMedia(() -> {
            stage.close();
            SwingUtilities.invokeLater(() -> GameLauncher.menuWindow = new MenuWindow(new MenuGUI()));
        });

        // Allow skipping on mouse click
        StackPane root = new StackPane(viewer);
        Scene scene = new Scene(root, 800, 420);
        scene.setOnMouseClicked(e -> {
            player.stop();
            stage.close();
            SwingUtilities.invokeLater(() -> GameLauncher.menuWindow = new MenuWindow(new MenuGUI()));
        });

        stage.setScene(scene);
        stage.setTitle("MegaBlast Mayhem Splash");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        player.play();
    }

    public static void playSplashVideo() {
        // Ensures JavaFX toolkit is initialized
        new JFXPanel();
        Application.launch(SplashVideoPlayer.class);
    }
}
