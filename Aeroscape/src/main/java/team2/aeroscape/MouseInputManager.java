package team2.aeroscape;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInputManager extends MouseAdapter {

    private GridRenderer gridRenderer;
    private boolean mousePressed;
    private MouseEvent mouseEvent;

    public MouseInputManager(GridRenderer gridRenderer) {
        this.gridRenderer = gridRenderer;
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

    public void mouseClicked() {
        if (mousePressed) {
            mousePressed = false;
            new Thread(() -> gridRenderer.handleMinerPlacement(mouseEvent)).start();
        }
    }
}
