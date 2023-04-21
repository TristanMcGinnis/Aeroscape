package team2.aeroscape;

/**
The Building class is an abstract class that extends Tile.
It provides a base for all buildings in the game.
*/
public abstract class Building extends Tile {

    /** The width of the building
     */
    protected int width;
    /** The height of the building
     */
    protected int height;
    
    /**
    * Constructor for the Building class.
    *
    * @param width  - The width of the building.
    * @param height - The height of the building.
    */
    
    public Building(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    
    /**
     * Constructor for the Building class that also takes a Tile as a parameter.
     *
     * @param width  - The width of the building.
     * @param height - The height of the building.
     * @param tile   - The Tile to copy.
     */
    public Building(int width, int height, Tile tile) {
        super(tile);
        this.width = width;
        this.height = height;
    }

    
    /**
     * Abstract method to update the state of the building.
     * Must be implemented by all subclasses.
     */
    public abstract void update();

}
