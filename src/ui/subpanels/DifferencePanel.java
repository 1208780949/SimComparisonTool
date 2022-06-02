package ui.subpanels;

import sim.Sim;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DifferencePanel extends Subpanel {

    private final Sim sim1;
    private final Sim sim2;
    private JLabel picture;

    public DifferencePanel(Sim sim1, Sim sim2) {
        this.sim1 = sim1;
        this.sim2 = sim2;
        add(picture);
    }

    @Override
    public void respond() {

        picture.setIcon(new ImageIcon(imgResize(new ImageIcon(imageSubtract(sim1.getPicture(), sim2.getPicture())))));

    }

    private BufferedImage imageSubtract(BufferedImage img1, BufferedImage img2) {

        // get size of picture
        int width = img1.getWidth();
        int height = img1.getHeight();

        // initialize arrays
        byte[][] subtractedGreyscale = new byte[width][height];
        int[] color1 = new int[3];
        int[] color2 = new int[3];
        Color difference;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // get green for each pixel and use it to convert to greyscale
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                color1 = img1.getRaster().getPixel(i, j, new int[3]);
                color2 = img2.getRaster().getPixel(i, j, new int[3]);

                // find difference
                difference = new Color(color1[0] - color2[0], color1[1] - color2[1], color1[2] - color2[2]);

                // greyscale difference
                bufferedImage.setRGB(i, j, difference.getRGB());

            }
        }

        return bufferedImage;
    }

}
