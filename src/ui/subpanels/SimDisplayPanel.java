package ui.subpanels;

import sim.Sim;

import javax.swing.*;
import java.awt.*;

public class SimDisplayPanel extends Subpanel {

    private final Sim sim;
    JLabel picture;

    public SimDisplayPanel(Sim sim) {

        this.sim = sim;
        picture = new JLabel();
        add(picture);

    }

    @Override
    public void respond() {
        sim.newSim();
        if (sim.isValid()) {
            showPicture();
        }
        ((Subpanel) getParent().getComponent(2)).showPicture();
        ((Subpanel) getParent().getComponent(3)).showPicture();
    }

    @Override
    public void showPicture() {
        picture.setIcon(new ImageIcon(imgResize(new ImageIcon(sim.getPicture()))));
    }

    public void updatePicture() {
        sim.newPicture();
        showPicture();
        ((Subpanel) getParent().getComponent(2)).showPicture();
        ((Subpanel) getParent().getComponent(3)).showPicture();
    }
}
