package team2.aeroscape;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
The MouseInputManager class manages mouse input for the game.
*/
public class MouseInputManager extends MouseAdapter {

    private boolean mousePressed;
    private MouseEvent mouseEvent;

    
    /**
    Creates a new MouseInputManager with the default settings.
    */
    public MouseInputManager() {
        mousePressed = false;
    }

    /**
    Responds to a mousePressed event by setting mousePressed to true and setting the most recent MouseEvent to e.
    @param e the MouseEvent associated with the mousePressed event.
    */
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseEvent = e;
    }

    /**
    Responds to a mouseReleased event by setting mousePressed to false.
    @param e the MouseEvent associated with the mouseReleased event.
    */
    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    
    /**
    Returns the most recent mouse click event if the mouse is pressed.
    @return the most recent mouse click event if the mouse is pressed; otherwise, null.
    */
    public MouseEvent getMouseClickEvent() {
        if (mousePressed) {
            mousePressed = false;
            return mouseEvent;
        }
        return null;
    }
}
