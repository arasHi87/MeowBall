package main.base;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    public void play(String soundName, boolean loop) {
        try {
            File file = new File(Utils.getSoundPath(soundName));
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
