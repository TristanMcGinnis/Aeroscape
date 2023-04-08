package team2.aeroscape;
import java.awt.Graphics2D;

/**
 * The 'Camera' class is responsible for defining a camera object that can be used to follow a target in game.
 * It has 'x' and 'y' coordinates that represent the position of the camera. The 'follow()' method takes the
 * coordinates of a target, as well as the width and height of the game screen, and updates the camera's
 * position so that is centered on target.
 */
public class Camera {
    private int x;
    private int y;
    private double zoom;
    
    public Camera(int x, int y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }
    
/**
 * The follow() method takes a target position (targetX, targetY) and the width and height of the game screen
 * (w, h) as arguments. It then calculates the new position of the camera sot hat it is centered on the target,
 * and updates the 'x' and 'y' coordinates of the camera accordingly.
 */

    public void follow(int targetX, int targetY, int w, int h) {
        x = (int) ((targetX * zoom) - (w / 2));
        y = (int) ((targetY * zoom) - (h / 2));
    }

    
/**
 * The applyTransform() method takes a Graphics2D object and applies a translation transform to it based on
 * the camera's position. This is used to render the game entities relative to the camera's position.
 */
    public void applyTransform(Graphics2D g2d) {
        g2d.translate(-x, -y);
        g2d.scale(zoom, zoom);

    }

    
/**
 * The resetTransform() method takes a Graphics2D object and resets the translation transform applied by
 * the applyTransform() method. This is used to ensure that subsequent rendering operations are not
 * affected by the camera transform.
 */    
    public void resetTransform(Graphics2D g2d) {
        g2d.scale(1 / zoom, 1 / zoom);
        g2d.translate(x, y);
    }
    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public double getZoom() {
        return zoom;
    }
    
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }
}
