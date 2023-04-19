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
    private long lastTime, startTime;
    private int difficulty;
    private Missile[] flyers;
    private double[] worldSpace = new double[2];
    
    
    public EnemyHandler(int difficulty, long time, int tilesX, int tilesY, int meterScale) {
        this.difficulty = difficulty;
        this.startTime = time;  
        this.lastTime = time;
        this.worldSpace[0] = tilesX * meterScale; this.worldSpace[1] = tilesY * meterScale;
    }
    
 
    /*
    Adds a missile to the flyers list.
    Returns true if sucessful
    Used exclusively by the update method.
    */
    private boolean initMissile(Tile location, Building target) {
        BMissile missile = new BMissile(location, target);
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
