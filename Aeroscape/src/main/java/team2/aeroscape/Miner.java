package team2.aeroscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;


public class Miner extends Building {
    private int miningSpeed = 1;
    private long lastMiningTime = 0; // Store the time of the last mining action
    private long miningInterval = 5000; // Set the idle time between mining actions in milliseconds
    private Inventory inventory;
    private Tile tile;
    private static BufferedImage minerTexture;
    
    public Miner(Inventory inventory, Tile tile) {
        super(50, 50, tile); // Set the width and height of the Miner building
        color = new Color(0,0,255);
        this.inventory = inventory;
        this.tile = tile;
    }

    static {
        try {
            Path minerPath = Paths.get("src/main/resources/textures/miner.png");



            minerTexture = ImageIO.read(Files.newInputStream(minerPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void update() {
        if (tile != null) {
            // Check if enough time has passed since the last mining action
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMiningTime >= miningInterval) {
                // Check if the tile has any resources
                int[] resources = tile.getResources();
                boolean mined = false;
                for (int i = 0; i < resources.length; i++) {
                    if (resources[i] > 0) {
                        // Remove some amount of the resource from the tile and add it to the inventory
                        int amount = Math.min(resources[i], miningSpeed);
                        tile.addResource(i, -amount);
                        resources[i] -= amount; // Decrease the amount of the resource in the tile
                        switch (i) {
                            case 0:
                                inventory.addIron(amount);
                                break;
                            case 1:
                                inventory.addCopper(amount);
                                break;
                            case 2:
                                inventory.addGold(amount);
                                break;
                            case 3:
                                inventory.addCoal(amount);
                                break;
                        }
                        mined = true;
                        System.out.println("Resource mined");
                    }
                }
                if (mined) {
                    // If resources were mined, update the lastMiningTime
                    lastMiningTime = currentTime;
                    
                    int totalResources = 0;
                    
                    for (int resource : resources) {
                        totalResources += resource;
                    }
                    if (totalResources <= 0) {
                        System.out.println("Mined all resources");
                    }
                }
            }
        }
    }



    @Override
    public void render(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.BLUE);
        int minerSize = 50; // Set the size of the miner

        int screenX = (int) ((x - camera.getX()) * camera.getZoom());
        int screenY = (int) ((y - camera.getY()) * camera.getZoom());

        // Render the miner at the calculated on-screen position
        g2d.drawImage(TextureEngine.minerTexture, screenX, screenY, (int) (minerSize * camera.getZoom()), (int) (minerSize * camera.getZoom()), null);
        
    }
        public BufferedImage getTexture() {
        return minerTexture;
    }   
}