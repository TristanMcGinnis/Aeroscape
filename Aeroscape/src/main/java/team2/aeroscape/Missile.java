/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * Base class for implementing other types of missiles
 */
public abstract class Missile {
    protected static float burnoutVel = 2000; // 2 km/s
    protected double[] V = new double[3]; //Velocity
    protected double[] P = new double[3]; //Position
    protected double[] targetP = new double[3]; //Target Position
    protected long lastTime;
    
    Missile(Tile pos) {
        P[0] = pos.x*1000; P[1] = pos.y*1000; P[2] = 0; //Init Position meters
        V[0] = burnoutVel; V[1] = 0; V[2] = 0; //Arbitary assignment for redundancy
        lastTime = System.currentTimeMillis();
    }
    
    /*
    Finds the desired heading for static target.
    */
    protected double getTargetHeading(double targetX, double targetY) {
        double dX = targetX - P[0];
        double dY = targetY - P[1];
        return Math.atan2(dY, dX);
    }
    
    
    /*
    Given vMag and heading, aligns X,Y component of objects velocity
    */
    protected void alignVel2Heading(double vMag, double azRads) {
        V[0] = vMag * Math.cos(azRads);
        V[1] = vMag * Math.sin(azRads);
    }
    
    /*
    Updates missile
    */
    protected boolean update() {
        long currTime = System.currentTimeMillis();
        float timeDelta = (currTime - lastTime) / 1000.0f; //Time in seconds 
        lastTime = currTime;
        
        P[0] = P[0] + V[0]*timeDelta;
        P[1] = P[1] + V[1]*timeDelta;
        //P[3] arbitrary for now
        
        
        
        return true;
        
    }
    
}
