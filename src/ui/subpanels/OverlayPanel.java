package ui.subpanels;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class OverlayPanel extends Subpanel {

    private BufferedImage overlayImg;
    private final JLabel picture;
    private float alpha = 0.5f; // default 50% transparency for overlay

    public OverlayPanel() {
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setVerticalAlignment(JLabel.CENTER);

        add(picture);
    }

    public void showPicture() {
    }



}
