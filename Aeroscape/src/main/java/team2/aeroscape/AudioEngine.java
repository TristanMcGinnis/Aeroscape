package team2.aeroscape;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


class AudioEngine {

    private Map<String, Clip> audioClips;

    public AudioEngine() {
        audioClips = new HashMap<>();
    }

    public void loadAudio(String name, String path) {
        try {
            File audioFile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            audioClips.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading audio: " + path);
            e.printStackTrace();
        }
    }

    public void playSound2D(String name, float volume) {
        Clip clip = audioClips.get(name);
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Error: Audio not found: " + name);
        }
    }

    public void loopSound2D(String name, float volume, int loopCount) {
        Clip clip = audioClips.get(name);
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.setFramePosition(0);
            clip.loop(loopCount);
        } else {
            System.err.println("Error: Audio not found: " + name);
        }
    }
}