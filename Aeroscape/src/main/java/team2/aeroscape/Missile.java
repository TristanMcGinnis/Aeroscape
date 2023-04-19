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
    protected float burnoutVel = 2000; // 2 km/s
    protected double[] V = new double[3]; //Velocity
    protected double[] P = new double[3]; //Position
    protected static long maxFlightTime = 120; //seconds
    protected long lastTime, launchTime; //Time checks
    protected int killRadius = 200; //Tolerance range for effective kill in meters
    boolean active = true;
    
    
    
    Missile(Tile pos) {
        P[0] = pos.x*1000; P[1] = pos.y*1000; P[2] = 0; //Init Position meters
        V[0] = burnoutVel; V[1] = 0; V[2] = 0; //Arbitary assignment for redundancy
        lastTime = System.currentTimeMillis();
        launchTime = lastTime * 1000;
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
    
    protected void destroy() {
        active = false;
    }
    
    
    /*
    Returns the 2D slant range between this and its target.
    */
    protected double getSlantRange(double[] targetP) {
        return Math.sqrt(Math.pow(targetP[0] - this.P[0], 2) + Math.pow(targetP[1] - this.P[1],2));
    }
    

}
