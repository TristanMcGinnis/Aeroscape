package team2.aeroscape;

import java.awt.Color;
import java.awt.Graphics2D;

public class Miner extends Building {
    private int miningSpeed = 1;
    private Grid grid;
    private Inventory inventory;
    
    public Miner(Grid grid, Inventory inventory) {
        super(50, 50); // Set the width and height of the Miner building
        this.grid = grid;
        this.inventory = inventory;
    }
    
        public Miner(Grid grid, Inventory inventory, Tile tile) {
        super(50, 50, tile); // Set the width and height of the Miner building
        color = new Color(0,0,255);
        this.grid = grid;
        this.inventory = inventory;
    }

    @Override
    public void update() {
        // Find the tile that the miner is on
        int tileX = (int) Math.floor((x + width/2) / grid.getGridSize());
        int tileY = (int) Math.floor((y + height/2) / grid.getGridSize());
        Tile tile = grid.getTile(tileX, tileY);

        // Check if the tile has any resources
        int[] resources = tile.getResources();
        boolean mined = false;
        for (int i = 0; i < resources.length; i++) {
            if (resources[i] > 0) {
                // Remove some amount of the resource from the tile and add it to the inventory
                int amount = Math.min(resources[i], miningSpeed);
                tile.addResource(i, -amount);
                switch (i) {
                    case 0 -> inventory.addIron(amount);
                    case 1 -> inventory.addCopper(amount);
                    case 2 -> inventory.addGold(amount);
                    default -> {
                    }
                }
                mined = true;
                break;
            }
        }

        if (mined) {
            System.out.println("Resource mined");
        }
    }


    @Override
    public void render(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.BLUE);
        int minerSize = 50; // Set the size of the miner
        g2d.fillRect((int) ((camera.getX() - x) / camera.getZoom()), (int) ((y - camera.getY()) / camera.getZoom()), minerSize, minerSize);
    }
}