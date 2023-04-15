/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * SAM Class(Surface Air Missile)
 * Launched by player built SAM_PLATFORMS at enemy Missiles
 * 
 */
public class SAM extends Missile {
    static float burnOutVel = 4000; //m/s  //Each grid with be 1km^2
    
    
    
    //Initialize position of SAM at the position of the platfrom
    public SAM(Tile plt, double targetX, double targetY) {
        super(plt);
        this.targetP[0] = targetX; this.targetP[1] = targetY; this.targetP[2] = 0;
    }
    

    
    
    /*
    Finds the heading(Azimuth) need to intercept a given target.
    */
    public void findInterceptAz(BMissile target) {
        
    }
    
    
    /*
    Discrete Brute Force Algorithm for solving for launch Az, at most minimal time.
    Multivariate Equation Solver would be more ideal in future 
    Returns Best Azimuth[0], Best Time[1], minDistance[2]
    */
    public double[] MinTimeIntercept_BF(BMissile target) {
        double dt = .1; //Time increment
        double minDistance = Double.MAX_VALUE;
        double bestAz = 0;
        double bestT = 0;
        double maxT = 500;
        
        double tolerance = 100; //meters
        
        for(double t = 0; t<=maxT;t += dt) {
            double[] tPos = {target.P[0] + target.V[0]*t,
                            target.P[1] + target.V[1]*t};
            
            double az = this.getTargetHeading(tPos[0],tPos[1]);

            double[] iPos = {this.P[0] + burnoutVel*Math.cos(az)*t,
                            this.P[1] + burnoutVel*Math.sin(az)*t};

            double deltaDist = Math.sqrt(Math.pow(tPos[0] - iPos[0], 2) + Math.pow(tPos[1] - iPos[1], 2));

            if (deltaDist < tolerance) {
                minDistance = deltaDist;
                bestAz = az;
                bestT = t;
                
                double[] props = {bestAz, bestT, minDistance};
                return props; //Return the properties of suitable intercept.
            }
        }
        
        double[] props = {bestAz, bestT, minDistance};
        return props; //Returns default state if not found
    }
}
