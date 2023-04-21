package team2.aeroscape;

/**
The Inventory class represents the inventory of the player, which contains the amounts of different resources and ingots.
This class is responsible for adding and retrieving resources and ingots from the inventory.
*/
public class Inventory {
    private int iron;
    private int copper;
    private int gold;
    private int coal;
    
    private int iron_ingot;
    private int copper_ingot;
    private int gold_ingot;
    
    
    /**
    * Constructs a new `Inventory` object with default values for each resource and ingot.
    */
    public Inventory() {
        iron = 0;
        copper = 0;
        gold = 0;
        coal = 0;
        
        iron_ingot = 0;
        copper_ingot = 0;
        gold_ingot = 0;
    }

    
    /**
    * Returns the amount of iron ingots in the inventory.
    *
    * @return The amount of iron ingots in the inventory.
    */
    public int getIronIngot()
    {
        return iron_ingot;
    }
    
    
    /**
    * Adds the specified amount of iron ingots to the inventory.
    *
    * @param amount The amount of iron ingots to add to the inventory.
    */
    public void addIronIngot(int amount)
    {
        System.out.println("Iron_Ingot Added");
        iron_ingot += amount;
    }
    
    
    /**
    * Returns the amount of copper ingots in the inventory.
    *
    * @return The amount of copper ingots in the inventory.
    */
    public int getCopperIngot()
    {
        return copper_ingot;
    }
    
    
    /**
    * Adds the specified amount of copper ingots to the inventory.
    *
    * @param amount The amount of copper ingots to add to the inventory.
    */
    public void addCopperIngot(int amount)
    {
        System.out.println("Copper_Ingot Added");
        copper_ingot += amount;
    }
    
    /**
     * Returns the amount of gold ingots in the inventory.
     *
     * @return The amount of gold ingots in the inventory.
     */
    public int getGoldIngot()
    {
        return gold_ingot;
    }

    /**
     * Adds the specified amount of gold ingots to the inventory.
     *
     * @param amount The amount of gold ingots to add to the inventory.
     */
    public void addGoldIngot(int amount)
    {
        System.out.println("Gold_Ingot Added");
        gold_ingot += amount;
    }

    /**
     * Returns the amount of iron in the inventory.
     *
     * @return The amount of iron in the inventory.
     */
    public int getIron() {
        return iron;
    }

    /**
     * Adds the specified amount of iron to the inventory.
     *
     * @param amount The amount of iron to add to the inventory.
     */
    public void addIron(int amount) {
        System.out.println("Iron Added");
        iron += amount;
    }

    /**
     * Returns the amount of copper in the inventory.
     *
     * @return The amount of copper in the inventory.
     */
    public int getCopper() {
        return copper;
    }

    /**
     * Adds the specified amount of copper to the inventory.
     *
     * @param amount The amount of copper to add to the inventory.
     */
    public void addCopper(int amount) {
        System.out.println("Copper Added");
        copper += amount;
    }

    /**
     * Returns the amount of gold in the inventory.
     *
     * @return The amount of gold in the inventory.
     */
    public int getGold() {
        return gold;
    }

    
    /**
     * Adds the specified amount of gold to the inventory.
     *
     * @param amount The amount of gold to add to the inventory.
     */
    public void addGold(int amount) {
        System.out.println("Gold Added");
        gold += amount;
    }
    
    
    /**
     * Returns the amount of coal in the inventory.
     *
     * @return The amount of coal in the inventory.
     */
    public int getCoal() {
        return coal;
    }

    
    /**
     * Adds the specified amount of coal to the inventory.
     *
     * @param amount The amount of coal to add to the inventory.
     */
    public void addCoal(int amount) {
        System.out.println("Coal");
        coal += amount;
    }
}
