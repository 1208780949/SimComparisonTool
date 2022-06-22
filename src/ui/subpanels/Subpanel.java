package ui.subpanels;

import main.SimComparisonTool;
import ui.DispPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.BorderFactory.createLineBorder;

public class Subpanel extends JPanel {

    protected final JLabel picture;

    public Subpanel() {

        this.picture = new JLabel();

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
     * Renders the default picture
     */
    public void showDefaultPic() {
        try {
            BufferedImage icon = ImageIO.read(new File(System.getenv("LOCALAPPDATA") + File.separator + SimComparisonTool.SCT_FOLDER_NAME + File.separator + "vettel.png"));
            picture.setIcon(new ImageIcon(imgResize(icon)));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    protected BufferedImage imgResize(BufferedImage img) {
        // get image size
        double imgWidth = img.getWidth();
        double imgHeight = img.getHeight();

        // calculate the ratio of image size and panel size
        double widthRatio = imgWidth / getWidth();
        double heightRatio = imgHeight / getHeight();

        // the bigger ratio is the limiting factor
        double rescaleRatio = Math.max(widthRatio, heightRatio);

        // rescaled size
        int newImgWidth = (int) Math.round(imgWidth / rescaleRatio);
        int newImgHeight = (int) Math.round(imgHeight / rescaleRatio);

        // rescale image
        BufferedImage result = new BufferedImage(newImgWidth, newImgHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(img.getScaledInstance(newImgWidth, newImgHeight, ((DispPane) this.getParent()).getImageQuality()), 0, 0, null);
        return result;
    }

}
