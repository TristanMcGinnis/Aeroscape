package team2.aeroscape;


/**
 *
 * SAM Class(Surface Air Missile)
 * Launched by player built SAM_PLATFORMS at enemy Missiles
 * 
 */
public class SAM extends Missile {
    
    private Missile target;
    private int killRadius = 100;
    
    //Initialize position of SAM at the position of the platfrom
    public SAM(Tile plt, Missile target) {
        super(plt);
        this.target = target;
        
        
        if (target != null) { //If has actual target
            double[] props = MinTimeIntercept_BF(target); //az, time, min d2t
            this.alignVel2Heading(burnoutVel, props[0]);
        } 
    }
    
    /*
    Update method for a SAM missile(Missile with a nonmoving ground target).
    Checks if within hit tolerance of target, target no longer active, and checks if timeout has been hit
    Returns false when condition is met
    Returns true as long as missile is active
    */
    protected boolean update() {
        long currTime = System.currentTimeMillis();
        if (1000*currTime - launchTime > maxFlightTime || target == null) {
            return false;
        }
        float timeDelta = (currTime - lastTime) / 1000.0f; //Time in seconds 
        lastTime = currTime;
        
        P[0] = P[0] + V[0]*timeDelta;
        P[1] = P[1] + V[1]*timeDelta;
        //P[3] arbitrary for now
        
        
        //Kill target
        if (getSlantRange(target.P) < killRadius) {
            target.destroy(); //Sets target active flag to false
            this.destroy(); //Sets self active flag to false although probably redundant.
            return false;
        }
        return true;
    }
    
    
    
    /*
    Discrete Brute Force Algorithm for solving for launch Az, at most minimal time.
    Multivariate Equation Solver would be more ideal in future 
    Returns Best Azimuth[0], Best Time[1], minDistance[2]
    */
    private double[] MinTimeIntercept_BF(Missile target) {
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
