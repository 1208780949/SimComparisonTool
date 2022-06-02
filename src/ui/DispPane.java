package ui;

import sim.Sim;
import ui.subpanels.DifferencePanel;
import ui.subpanels.OverlayPanel;
import ui.subpanels.SimDisplayPanel;
import ui.subpanels.Subpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static javax.swing.BorderFactory.createLineBorder;

public class DispPane extends JPanel {

    // initialize

    public DispPane() {
        setLayout(new GridLayout(2, 2));
        addPanels();
        addMouseListener(new MouseFocusListener(this));
    }

    /**
     * Adds JPanels to each of the four slots in GridLayout in contentPane
     */
    private void addPanels() {

        // new sims
        Sim sim1 = new Sim();
        Sim sim2 = new Sim();

        // add subpanels
        add(new SimDisplayPanel(sim1));
        add(new SimDisplayPanel(sim2));
        add(new DifferencePanel(sim1, sim2));
        add(new OverlayPanel());
    }

    public void panelClicked(int i) {
        if (i == -1) {
            getParent().requestFocus();
        } else {
            Subpanel subpanel = (Subpanel) getComponent(i);
            if (subpanel.hasFocus()) {
                subpanel.respond();
            } else {
                subpanel.requestFocus();
            }
        }
    }
}
