package team2.aeroscape;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
The KeyboardInputManager class is responsible for managing keyboard input from the user. It extends KeyAdapter and
keeps track of whether the up, down, left, or right arrow keys are currently being pressed.
*/
public class KeyboardInputManager extends KeyAdapter {
    private boolean up, down, left, right;

    
    /**
    * Constructs a new `KeyboardInputManager` object and initializes the key variables to `false`.
    */
    public KeyboardInputManager() {
        up = down = left = right = false;
    }

    
    /**
    * Overrides the `keyPressed` method in `KeyAdapter` and sets the appropriate key variable to `true` when a key is pressed.
    *
    * @param e The key event that was triggered by the user.
    */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
        }
    }

    
    /**
    * Overrides the `keyReleased` method in `KeyAdapter` and sets the appropriate key variable to `false` when a key is released.
    *
    * @param e The key event that was triggered by the user.
    */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
        }
    }

    
    /**
    * Returns `true` if the up arrow key is currently being pressed.
    *
    * @return `true` if the up arrow key is currently being pressed, `false` otherwise.
    */
    public boolean isUp() {
        return up;
    }

    
    /**
    * Returns `true` if the down arrow key is currently being pressed.
    *
    * @return `true` if the down arrow key is currently being pressed, `false` otherwise.
    */
    public boolean isDown() {
        return down;
    }

    
    /**
    * Returns `true` if the left arrow key is currently being pressed.
    *
    * @return `true` if the left arrow key is currently being pressed, `false` otherwise.
    */
    public boolean isLeft() {
        return left;
    }

    
    /**
    * Returns `true` if the right arrow key is currently being pressed.
    *
    * @return `true` if the right arrow key is currently being pressed, `false` otherwise.
    */
    public boolean isRight() {
        return right;
    }
}
