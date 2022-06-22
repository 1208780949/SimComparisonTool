package ui.subpanels;

import main.SimComparisonTool;
import sim.Sim;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimDisplayPanel extends Subpanel {

    private final Sim sim;

    public SimDisplayPanel(Sim sim) {
        super();
        this.sim = sim;
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
