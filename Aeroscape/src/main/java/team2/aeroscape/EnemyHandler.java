/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * Class responsible for handling the spawning of enemies into the game space
 */
public class EnemyHandler {
    private int difficulty;
    private Missile[] flyers;
    private int meterScale;
    
    
    public EnemyHandler(int difficulty, int squareMetersPerTile) {
        this.difficulty = difficulty;
        this.meterScale = squareMetersPerTile;
    }
    
    /*
    Adds a missile to the flyers list.
    Returns true if sucessful
    Used exclusively by the update method.
    */
    private boolean addMissile(Tile location, Tile target) {
        return true;
    }
    
    /*
    Updates the EnemyHandler class on a time basis.
    Performs probabilistic based spawning of enemy scaled by time and difficuly.
    Returns false if no enemy was spawned. Returns true if so.
    */
    public boolean update() {
        return false;
    }
    
    
}
