package team2.aeroscape;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInputManager extends MouseAdapter {

    private boolean mousePressed;
    private MouseEvent mouseEvent;

    public MouseInputManager() {
        mousePressed = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseEvent = e;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    public MouseEvent getMouseClickEvent() {
        if (mousePressed) {
            mousePressed = false;
            return mouseEvent;
        }
        return null;
    }
}
