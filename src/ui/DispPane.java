package ui;

import main.SimComparisonTool;
import sim.Sim;
import ui.subpanels.DifferencePanel;
import ui.subpanels.OverlayPanel;
import ui.subpanels.SimDisplayPanel;
import ui.subpanels.Subpanel;

import javax.swing.*;
import java.awt.*;

public class DispPane extends JPanel {

    private final SimDisplayPanel simDisplayPanelLeft;
    private final SimDisplayPanel simDisplayPanelRight;
    private final DifferencePanel differencePanel;
    private final OverlayPanel overlayPanel;

    private int imageQuality = Image.SCALE_FAST;

    // initialize

    public DispPane() {

        this.simDisplayPanelLeft = new SimDisplayPanel(SimComparisonTool.sim1);
        this.simDisplayPanelRight = new SimDisplayPanel(SimComparisonTool.sim2);
        this.differencePanel = new DifferencePanel();
        this.overlayPanel = new OverlayPanel();

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
        add(differencePanel);
        add(overlayPanel);
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

    /**
     * Updates all four pictures
     */
    public void updatePictures() {
        simDisplayPanelLeft.updatePicture();
        simDisplayPanelRight.updatePicture();
        differencePanel.showPicture(false);
        overlayPanel.showPicture(false);
    }

    // setters

    public void setImageQuality(int imageQuality) {
        this.imageQuality = imageQuality;
    }


    // getters

    public DifferencePanel getDifferencePanel() {
        return differencePanel;
    }

    public int getImageQuality() {
        return imageQuality;
    }

    public OverlayPanel getOverlayPanel() {
        return overlayPanel;
    }
}
