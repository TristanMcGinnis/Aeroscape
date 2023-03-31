/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;
import java.awt.Graphics2D;

/**
 *
 * @author Jacob
 */
public class Camera {
    private int x;
    private int y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void follow(int targetX, int targetY, int w, int h) {
        x = targetX - w/2;
        y = targetY - h/2;
    }

    public void applyTransform(Graphics2D g2d) {
        g2d.translate(-x, -y);
    }

    public void resetTransform(Graphics2D g2d) {
        g2d.translate(x, y);
    }
}
