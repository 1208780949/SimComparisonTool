package ui.subpanels;

import sim.Sim;

import javax.swing.*;


public class SimDisplayPanel extends Subpanel {

    private final Sim sim;

    public SimDisplayPanel(Sim sim) {
        super();
        this.sim = sim;
    }

    @Override
    public void respond() {
        sim.newSim();
        if (sim.isValid()) {
            showPicture();
        }
        ((DifferencePanel) getParent().getComponent(2)).showPicture(false);
        ((OverlayPanel) getParent().getComponent(3)).showPicture();
    }

    @Override
    public void showPicture() {

        sim.setResizedCopy(imgResize(sim.getPicture()));
        picture.setIcon(new ImageIcon(sim.getResizedCopy()));
    }

    public void updatePicture() {
        sim.newPicture();
        showPicture();
    }
}
