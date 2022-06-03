package ui;

import main.SimComparisonTool;
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

    private SimDisplayPanel simDisplayPanelLeft;
    private SimDisplayPanel simDisplayPanelRight;

    // initialize

    public DispPane() {

        this.simDisplayPanelLeft = new SimDisplayPanel(SimComparisonTool.sim1);
        this.simDisplayPanelRight = new SimDisplayPanel(SimComparisonTool.sim2);

        setLayout(new GridLayout(2, 2));
        addPanels();
        addMouseListener(new MouseFocusListener(this));
    }

    /**
     * Adds JPanels to each of the four slots in GridLayout in contentPane
     */
    private void addPanels() {

        // add subpanels
        add(simDisplayPanelLeft);
        add(simDisplayPanelRight);
        add(new DifferencePanel());
        add(new OverlayPanel());
    }

    /**
     * Light up the border green if it was not in focus before
     * and request focus.
     * If it is already in focus, respond to the click.
     * @param i panel index
     */
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

    // getter

    public SimDisplayPanel getSimDisplayPanelLeft() {
        return simDisplayPanelLeft;
    }

    public SimDisplayPanel getSimDisplayPanelRight() {
        return simDisplayPanelRight;
    }
}
