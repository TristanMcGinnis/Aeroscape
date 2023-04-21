package team2.aeroscape;

/**
 * Class responsible for handling the spawning of enemies into the game space
 */
public class EnemyHandler {
    private int difficulty;
    private Missile[] flyers;
    private int meterScale;

    /** Default constructor
     * The default constructor for the enemy handler.
     * @param difficulty The difficulty of the game.
     * @param squareMetersPerTile The scale of the tiles.
     */
    public EnemyHandler(int difficulty, int squareMetersPerTile) {
        this.difficulty = difficulty;
        this.meterScale = squareMetersPerTile;
    }
    
    /**
     * Adds a missile to the flyers list.
     * @param location The location of the tile.
     * @param target The tile target of the missile.
     * @return Returns true if successful.
     */
    private boolean addMissile(Tile location, Tile target) {
        return true;
    }

    /**
     * Updates EnemyHandler class on a time basiss.
     * @return Returns false if no enemy was spawned, and true if so.
     */
    public boolean update() {
        return false;
    }
}
