package team2.aeroscape;

import java.awt.Color;
import java.awt.Graphics2D;

public class Miner extends Building {
    
    private int x;
    private int y;
    private int miningSpeed = 1;
    private Grid grid;
    private Inventory inventory;
    
    public Miner(int x, int y, Grid grid, Inventory inventory) {
        super(x, y, 50, 50); // Set the width and height of the Miner building
        this.x= x;
        this.y = y;
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
                if (i == 0) {
                    inventory.addIron(amount);
                } else if (i == 1) {
                    inventory.addCopper(amount);
                } else if (i == 2) {
                    inventory.addGold(amount);
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
        g2d.fillRect((int) ((x - camera.getX()) / camera.getZoom()), (int) ((y - camera.getY()) / camera.getZoom()), minerSize, minerSize);
    }
}