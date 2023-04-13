/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * @author nndcp
 */
public class SAM {
    static float burnOutVel = 4000; //m/s
    //Each grid with be 1km^2
    
    float posX, posY, targetX, targetY;
    long lastTime;
    
    
    
    //Initialize position of SAM at the position of the platfrom
    public SAM(int pltX, int pltY, float targetX, float targetY) {
        posX = pltX;
        posY = pltY;
        this.targetX = targetX;
        this.targetY = targetY;
        lastTime = System.nanoTime();
    }
    
    public void calcLaunchAzim() {
        
    }
    
    /*
    Update the position of the SAM along flyout
    Returns true once the SAM has impacted or expired.
    */
    public boolean update() {
        long currTime = System.nanoTime();
        long deltaTime = currTime - lastTime;
        lastTime = currTime;
        
        
        return false;
    }
    
    public void targetCheck(float targetX, float targetY) {
        
    }
}
