package ui.subpanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static javax.swing.BorderFactory.createLineBorder;

public class Subpanel extends JPanel {

    public Subpanel() {
        setBorder(createLineBorder(Color.BLACK));
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(createLineBorder(Color.GREEN));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(createLineBorder(Color.BLACK));
            }
        });
    }

    /**
     * Respond to mouse click when it is already in focus
     */
    public void respond() {
        // intentionally left blank as a boilerplate for child classes
    }

}
