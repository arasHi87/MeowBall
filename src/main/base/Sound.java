package main.base;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;
    private String fileName;

    // Constructor to construct each element of the enum with its own sound file.

    public Sound() {
    }

    public void play(String soundName, boolean loop) {
        try {
            switch (soundName) {
                case "smash":
                    fileName = "src/main/resources/sounds/fire_sound.wav";
                    break;
                case "cheer":
                    fileName = "src/main/resources/sounds/cheer.wav";
                    break;
                case "hit":
                    fileName = "src/main/resources/sounds/hit.wav";
                    break;
                case "select":
                    fileName = "src/main/resources/sounds/select.wav";
                    break;
                case "start":
                    fileName = "src/main/resources/sounds/start.wav";
                    break;
                case "fail":
                    fileName = "src/main/resources/sounds/fail.wav";
                    break;
                case "game":
                    fileName = "src/main/resources/sounds/game.wav";
                    break;
                default:
                    break;
            }
            File file = new File(fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (loop == true) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException e) {
            System.out.println("UnsupportedAudioFileExceptio");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("LineUnavailable");
            e.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }
}
