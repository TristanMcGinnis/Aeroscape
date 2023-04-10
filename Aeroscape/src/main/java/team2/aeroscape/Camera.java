package team2.aeroscape;

import java.awt.Graphics2D;

/**
 * The `Camera` class is responsible for defining a camera object that can be used to follow a target in the game.
 * It has `x` and `y` coordinates that represent the position of the camera, as well as a `zoom` factor that
 * determines the scale of the game world. The `follow()` method takes the coordinates of a target, as well as
 * the dimensions of the game screen, and updates the camera's position so that it is centered on the target.
 */
public class Camera {
    private int x;
    private int y;
    private double zoom;
    
    /**
     * Constructs a new `Camera` object with the specified position and zoom factor.
     */
    public Camera(int x, int y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }
    
    /**
     * Centers the camera on the specified target position with the specified screen dimensions.
     */
    public void follow(int targetX, int targetY, int screenWidth, int screenHeight) {
        x = (int) ((targetX * zoom) - (screenWidth / 2));
        y = (int) ((targetY * zoom) - (screenHeight / 2));
    }
    
    /**
     * Applies a translation and scaling transform to the specified `Graphics2D` object based on the camera's position and zoom factor.
     */
    public void applyTransform(Graphics2D g2d) {
        g2d.translate(-x, -y);
        g2d.scale(zoom, zoom);
    }
    
    /**
     * Resets the translation and scaling transform applied by the `applyTransform()` method on the specified `Graphics2D` object.
     */
    public void resetTransform(Graphics2D g2d) {
        g2d.scale(1 / zoom, 1 / zoom);
        g2d.translate(x, y);
    }
    
    /**
     * Returns the current X coordinate of the camera.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the current Y coordinate of the camera.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Sets the X coordinate of the camera.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y coordinate of the camera.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Returns the current zoom factor of the camera.
     */
    public double getZoom() {
        return zoom;
    }
    
    /**
     * Sets the zoom factor of the camera.
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    
    /**
     * Linearly interpolates between two values `a` and `b` using the specified interpolation factor `t`.
     */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }
}
