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
            picture.setIcon(new ImageIcon(resize(new ImageIcon(sim.getPicture()))));
        }
    }

    /**
     * Resize the picture
     */
    private Image resize(ImageIcon imageIcon) {

        // get image size
        double imgWidth = imageIcon.getIconWidth();
        double imgHeight = imageIcon.getIconHeight();

        // calculate the ratio of image size and panel size
        double widthRatio = imgWidth / getWidth();
        double heightRatio = imgHeight / getHeight();

        // the bigger ratio is the limiting factor
        double rescaleRatio = widthRatio > heightRatio ? widthRatio : heightRatio;

        // rescaled size
        int newImgWidth = (int) Math.round(imgWidth / rescaleRatio);
        int newImgHeight = (int) Math.round(imgHeight / rescaleRatio);

        // rescale image
        return imageIcon.getImage().getScaledInstance(newImgWidth, newImgHeight, Image.SCALE_FAST);

    }

}
