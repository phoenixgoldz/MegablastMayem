import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

    static Clip MenuMusic;
    static Clip MenuMove;
    static Clip MenuSelect;
    static Clip BombDrop;
    static Clip BombExplode;
    static Clip Victory;
    static Clip GameSong;
    int useless;

    public Audio(int num) {
        useless = num;
    }

    public static void playMenu() {
        try {
            if (MenuMusic != null && MenuMusic.isOpen()) {
                MenuMusic.stop();
                MenuMusic.close();
            }

            AudioInputStream in1 = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/music/32. Main Theme.wav")
            );
            MenuMusic = AudioSystem.getClip();
            MenuMusic.open(in1);
            MenuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void stopMenu() {
        if (MenuMusic != null) {
            if (MenuMusic.isRunning()) {
                MenuMusic.stop();
            }
            if (MenuMusic.isOpen()) {
                MenuMusic.close();
            }
        }
    }
    public static void playMenuMove() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/sound effects/MenuMove.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playMenuSelect() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/sound effects/MenuSelect.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBombDrop() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/sound effects/BombDrop.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBombExplode() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/sound effects/8BitBombExplosion.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playVictory() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/sound effects/Victory.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playGameSong() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/music/60-Seconds-2020-03-22_-_8_Bit_Surf_-_FesliyanStudios.com_-_David_Renda.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playDeathSong() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    Audio.class.getResource("/sound effects/kirby-death-sound.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
