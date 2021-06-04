package main.base;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume >= 0f || volume <= 1f) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
    }

    public void play(String soundName, boolean loop) {
        try {
            File file = new File(Utils.getSoundPath(soundName));
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (soundName == "game" || soundName == "start") {
                setVolume(0.5f);
            }

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
