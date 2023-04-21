package team2.aeroscape;


/**
 * The `Player` class represents a player in the game. It has `x` and `y` coordinates that represent the
 * player's position, as well as `velX` and `velY` velocities that determine the player's movement speed.
 */
public class Player {
    private int x;
    private int y;
    private int velX;
    private int velY;

    /**
     * Constructs a new 'Player' object with the specified position.
     * @param x The x coordinate of the player.
     * @param y The y coordinate of the player.
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.velX = 0;
        this.velY = 0;
    }

    /**
     * Returns the current X coordinate of the player.
     * @return the current X coordinate of the player.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the current Y coordinate of the player.
     * @return the current Y coordinate of the player.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the current X velocity of the player.
     * @return the current X velocity of the player.
     */
    public int getVelX() {
        return velX;
    }

    /**
     * Returns the current Y velocity of the player.
     * @return the current Y velocity of the player.
     */
    public int getVelY() {
        return velY;
    }

    /**
     * Sets the X coordinate of the player.
     * @param x the X coordinate of the player.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y coordinate of the player.
     * @param y the Y coordinate of the player.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the X velocity of the player.
     * @param velX the X velocity of the player.
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }

    /**
     * Sets the Y velocity of the player.
     * @param velY the Y velocity of the player.
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * Sets the X velocity of the player to 0, stopping the player's movement in both directions.
     */
    public void stopX() {
        this.velX = 0;
    }
    
     /**
     * Sets the Y velocity of the player to 0, stopping the player's movement in both directions.
     */
    public void stopY() {
        this.velY = 0;
    }

    /**
     * Updates the position of the player based on its current velocity.
     */
    public void update() {
        x += velX;
        y += velY;
    }
}
