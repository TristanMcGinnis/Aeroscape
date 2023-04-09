package team2.aeroscape;

import java.awt.Color;
import java.awt.Graphics2D;

public class Miner extends Building {
    
    private int drawX;
    private int drawY;
    
    public Miner(int x, int y) {
        super(x, y, 50, 50); // Set the width and height of the Miner building
        this.drawX = x;
        this.drawY = y;
    }

    @Override
    public void update() {
        // Implement mining logic here
    }

    @Override
    public void render(Graphics2D g2d, Camera camera) {
        System.out.println("Miner rendered");
        g2d.setColor(Color.BLUE);
        int minerSize = 50; // Set the size of the miner
        g2d.fillRect((int) ((x - camera.getX()) / camera.getZoom()), (int) ((y - camera.getY()) / camera.getZoom()), minerSize, minerSize);
    }
}
