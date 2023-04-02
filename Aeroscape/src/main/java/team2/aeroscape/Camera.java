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

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
/**
 * The follow() method takes a target position (targetX, targetY) and the width and height of the game screen
 * (w, h) as arguments. It then calculates the new position of the camera sot hat it is centered on the target,
 * and updates the 'x' and 'y' coordinates of the camera accordingly.
 */

    public void follow(int targetX, int targetY, int w, int h) {
        x = targetX - w/2 + 1000; // For some reason 1000 and 600 were the numbers needed to center the circle, probably a better way to do this.
        y = targetY - h/2 + 600;
    }

    
/**
 * The applyTransform() method takes a Graphics2D object and applies a translation transform to it based on
 * the camera's position. This is used to render the game entities relative to the camera's position.
 */
    public void applyTransform(Graphics2D g2d) {
        g2d.translate(-x, -y);
    }

    
/**
 * The resetTransform() method takes a Graphics2D object and resets the translation transform applied by
 * the applyTransform() method. This is used to ensure that subsequent rendering operations are not
 * affected by the camera transform.
 */    
    public void resetTransform(Graphics2D g2d) {
        g2d.translate(x, y);
    }
    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}