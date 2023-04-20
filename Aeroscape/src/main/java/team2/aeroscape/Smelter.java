package team2.aeroscape;


public class Smelter extends Building {
    
    private int smeltSpeed = 1;
    private long lastSmeltingTime = 0; // Store the time of the last smelting action
    private long smeltingInterval = 5000; // Set the idle time between smelting actions in milliseconds
    private Inventory inventory;
    
    
    public Smelter(Inventory inventory, Tile tile) {
        super(50, 50, tile); // Set the width and height of the Miner building
        this.inventory = inventory;
    }
    
    
    @Override
    public void update() {

        // Check if enough time has passed since the last smelting action
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSmeltingTime >= smeltingInterval) {

            // Check if the player has the resources needed
            int[] resources = new int[4];
            resources[0] = this.inventory.getIron();
            resources[1] = this.inventory.getCopper();
            resources[2] = this.inventory.getGold();
            resources[3] = this.inventory.getCoal();       

            boolean smelted = false;
            if(resources[3] > 0) // if player has coal
            {
                for (int i = 0; i < resources.length-1; i++) {//Goes through the first 3 resources (Raw: iron, copper gold)
                    if (resources[i] > 0) {
                        // Remove some amount of the resource from the inventory and add the smelted to inventory
                        int amount = Math.min(resources[i], smeltSpeed);
                        switch (i) {
                            case 0 -> {
                                inventory.addIron(-amount);//remove raw Iron from inventory
                                inventory.addIronIngot(amount);//Add iron ingot to inventory
                                smelted = true;
                            }
                            case 1 -> {
                                inventory.addCopper(-amount);
                                inventory.addCopperIngot(amount);
                                smelted = true;
                            }
                            case 2 -> {
                                inventory.addGold(-amount);
                                inventory.addGoldIngot(amount);
                                smelted = true;
                            }
                                default -> {
                            }
                        }
                    }
                }
            }

            if (smelted) {
                System.out.println("Resources smelted");
                inventory.addCoal(-1);
                lastSmeltingTime = currentTime; // Update the lastSmeltingTime
            }
        }
    }
}
