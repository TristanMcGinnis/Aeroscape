package team2.aeroscape;

/**
 * This abstract class serves as the base class for implementing other types of missiles in the game.
 * The missile has a defined burnout velocity, position, max flight time, kill radius, and time checks.
 */
public abstract class Missile {
    /**
     * Burnout velocity
     */
    protected float burnoutVel = 2000; // 2 km/s
    /**
     * Velocity
     */
    protected double[] V = new double[3]; 
    /**
     * Position
     */
    protected double[] P = new double[3];
    /**
     * Max flight time in seconds
     */
    protected static long maxFlightTime = 120;
    /**
     * Time checks
     */
    protected long lastTime, launchTime;
    /**
     * Tolerance range for effective kill in meters
     */
    protected int killRadius = 200; 
    boolean active = true;

    /**
     * Constructs a Missile object with an initial position based on a Tile object.
     * @param pos The position of the missile as a Tile object.
     */
    Missile(Tile pos) {
        P[0] = pos.x*1000; P[1] = pos.y*1000; P[2] = 0; // Init Position in meters
        V[0] = burnoutVel; V[1] = 0; V[2] = 0; // Arbitrary assignment for redundancy
        lastTime = System.currentTimeMillis();
        launchTime = lastTime * 1000;
    }

    /**
     * Calculates the desired heading for a static target.
     * @param targetX The X position of the target.
     * @param targetY The Y position of the target.
     * @return The desired heading in radians.
     */
    protected double getTargetHeading(double targetX, double targetY) {
        double dX = targetX - P[0];
        double dY = targetY - P[1];
        return Math.atan2(dY, dX);
    }

    /**
     * Aligns the X and Y components of the object's velocity given a magnitude and a heading.
     * @param vMag The magnitude of the velocity.
     * @param azRads The azimuth heading in radians.
     */
    protected void alignVel2Heading(double vMag, double azRads) {
        V[0] = vMag * Math.cos(azRads);
        V[1] = vMag * Math.sin(azRads);
    }

    /**
     * Sets the missile's active status to false, effectively destroying it.
     */
    protected void destroy() {
        active = false;
    }

    /**
     * Calculates the 2D slant range between the missile and its target.
     * @param targetP The position of the target as an array of [X, Y, Z] coordinates.
     * @return The 2D slant range between the missile and the target.
     */
    protected double getSlantRange(double[] targetP) {
        return Math.sqrt(Math.pow(targetP[0] - this.P[0], 2) + Math.pow(targetP[1] - this.P[1], 2));
    }
}
