package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public record MouseFocusListener(DispPane dispPane) implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        // intentionally left empty
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // intentionally left empty
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        // dispPane size
        double dispPaneWidthHalf = (double) dispPane.getWidth() / 2;
        double dispPaneHeightHalf = (double) dispPane.getHeight() / 2;

        // location that the mouse clicked on
        double x = e.getX();
        double y = e.getY();

        // determine focus
        if (x < dispPaneWidthHalf && y < dispPaneHeightHalf) {
            dispPane.panelClicked(0);
        } else if (x > dispPaneWidthHalf && y < dispPaneHeightHalf) {
            dispPane.panelClicked(1);
        } else if (x < dispPaneWidthHalf && y > dispPaneHeightHalf) {
            dispPane.panelClicked(2);
        } else if (x > dispPaneWidthHalf && y > dispPaneHeightHalf) {
            dispPane.panelClicked(3);
        } else {
            dispPane.panelClicked(-1);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // intentionally left empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // intentionally left empty
    }
}
