package ui.subpanels;

import ui.DispPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static javax.swing.BorderFactory.createLineBorder;

public class Subpanel extends JPanel {

    public Subpanel() {
        setLayout(new GridLayout(1, 1, 1, 1));
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

    /**
     * Show the picture
     */
    public void showPicture() {
        // intentionally left blank
    }

    /**
     * Resize the picture
     */
    protected Image imgResize(ImageIcon imageIcon) {

        // get image size
        double imgWidth = imageIcon.getIconWidth();
        double imgHeight = imageIcon.getIconHeight();

        // calculate the ratio of image size and panel size
        double widthRatio = imgWidth / getWidth();
        double heightRatio = imgHeight / getHeight();

        // the bigger ratio is the limiting factor
        double rescaleRatio = Math.max(widthRatio, heightRatio);

        // rescaled size
        int newImgWidth = (int) Math.round(imgWidth / rescaleRatio);
        int newImgHeight = (int) Math.round(imgHeight / rescaleRatio);

        // rescale image
        return imageIcon.getImage().getScaledInstance(newImgWidth, newImgHeight, ((DispPane) this.getParent()).getImageQuality());

    }

}
