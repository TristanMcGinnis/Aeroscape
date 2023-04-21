package team2.aeroscape;


/**
 * Ballistic Missile Class.
 * Type of missile used by enemy platforms
 */
public class BallisticMissile extends Missile {
    Building target;
    
    BallisticMissile(Tile pos, Building target) {
        super(pos);
        this.target = target;
        double az = getTargetHeading(target.x, target.y);
        this.alignVel2Heading(burnoutVel,az);
    }
    
    
    /**
    Update method for an enemy ballistic missile(Missile with a nonmoving ground target).
    Checks if within hit tolerance of target and checks if timeout has been hit
    Updates position in realspace.
    Returns false when condition is met
    Returns true when missile is still active
    * @return Returns false if the missile gets a kill and true if it does not.
    */
    protected boolean update() {
        long currTime = System.currentTimeMillis();
        if (1000*currTime - launchTime > maxFlightTime || !active || target == null) {
            return false;
        }
        float timeDelta = (currTime - lastTime) / 1000.0f; //Time in seconds 
        lastTime = currTime;
        
        P[0] = P[0] + V[0]*timeDelta;
        P[1] = P[1] + V[1]*timeDelta;
        //P[3] arbitrary for now
        
        double[] targetP = {target.x, target.y};
        if (getSlantRange(targetP) < killRadius) {
            //Destroy Tile
            this.destroy();
            return false;
        } 
        return true;
    }
    
}
