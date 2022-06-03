package ui.subpanels;

import sim.Sim;

import javax.swing.*;

public class SimDisplayPanel extends Subpanel {

    private final Sim sim;
    JLabel picture;

    public SimDisplayPanel(Sim sim) {

        this.sim = sim;
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setVerticalAlignment(JLabel.CENTER);
        add(picture);

    }

    @Override
    public void respond() {
        sim.newSim();
        if (sim.isValid()) {
            showPicture();
        }
        ((DifferencePanel) getParent().getComponent(2)).showPicture(false);
        ((Subpanel) getParent().getComponent(3)).showPicture();
    }

    @Override
    public void showPicture() {
        picture.setIcon(new ImageIcon(imgResize(new ImageIcon(sim.getPicture()))));
    }

    public void updatePicture() {
        sim.newPicture();
        showPicture();
    }
}
