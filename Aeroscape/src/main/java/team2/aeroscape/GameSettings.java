package team2.aeroscape;


/**
The GameSettings class provides a way for managing the game settings.
It provides a means of storing and loading settings such as sound options and difficulty level.
*/
public class GameSettings {
    private boolean soundEnabled;
    private int volumeVal;
    private int difficulty; //0-3 (Peaceful, Easy, Normal, Hard)
    
    /**
    * Constructor for the GameSettings class.
    * Sets default values for sound options and difficulty.
    */
    public GameSettings() {
        soundEnabled = true;
        volumeVal = 50;
        difficulty = 1;
    }
    
    /**
    * Constructor for the GameSettings class that takes a file name as a parameter.
    * Ideally, this constructor would interface with a JSON file to load the settings.
    *
    * @param fileName - The name of the JSON file to load.
    */
    public GameSettings(String fileName) {
        //Ideally this will interface with a JSON file
        //Ex. data = load(fileName); soundEnabled = data.soundEnabled;
    }
    
}
