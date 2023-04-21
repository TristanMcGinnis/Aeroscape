package team2.aeroscape;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
The AudioEngine class provides a simple interface for loading and playing audio files.
Uses javax.sound.sampled package to load and play audio files.
*/
class AudioEngine {

    private Map<String, Clip> audioClips;

    /**
     * Constructor for the AudioEngine class.
     * Initializes the HashMap that will hold the audio clips.
     */
    
    public AudioEngine() {
        audioClips = new HashMap<>();
    }
    
    /**
     * Loads an audio file into memory using the given name as the key.
     *
     * @param name - The name to associate with the loaded audio file.
     * @param path - The file path to the audio file.
     */

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
    
    /**
     * Plays a 2D sound effect with the given name at the given volume level.
     *
     * @param name   - The name of the sound effect to play.
     * @param volume - The volume level to play the sound effect at. Range from 0 to 1.
     */

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
    
    /**
     * Loops a 2D sound effect with the given name at the given volume level and loop count.
     *
     * @param name      - The name of the sound effect to loop.
     * @param volume    - The volume level to play the sound effect at. Range from 0 to 1.
     * @param loopCount - The number of times to loop the sound effect. Use Clip.LOOP_CONTINUOUSLY to loop continuously.
     */

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