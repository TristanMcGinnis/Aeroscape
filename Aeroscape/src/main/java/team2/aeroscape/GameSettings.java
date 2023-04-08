/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * @author nndcp
 */
public class GameSettings {
    private boolean soundEnabled;
    private int volumeVal;
    private int difficulty; //0-3 (Peaceful, Easy, Normal, Hard)
    
    public GameSettings() {
        soundEnabled = true;
        volumeVal = 50;
        difficulty = 1;
    }
    public GameSettings(String fileName) {
        //Ideally this will interface with a JSON file
        //Ex. data = load(fileName); soundEnabled = data.soundEnabled;
    }
    
}
