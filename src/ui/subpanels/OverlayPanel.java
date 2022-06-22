package ui.subpanels;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingDeque;

public class OverlayPanel extends Subpanel {

    private BufferedImage overlayImg;
    private float alpha = 0.5f; // default 50% transparency for overlay
    private boolean autoUpdate;

    public OverlayPanel() {
        super();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setVerticalAlignment(JLabel.CENTER);

        autoUpdate = SimComparisonTool.settingsIO.getBoolSetting(SettingsKeys.OVERLAY_PANE_AUTO_UPDATE);

        add(picture);
    }

    @Override
    public void respond() {
        showPicture(true);
    }

    public void showPicture(boolean forceUpdate) {

        // if the user is not forcing an update and auto update is not on, don't update
        if (!autoUpdate && !forceUpdate) {
            return;
        }

        // images
        BufferedImage sim1Pic = SimComparisonTool.sim1.getResizedCopy();
        BufferedImage sim2Pic = SimComparisonTool.sim2.getResizedCopy();

        if (sim1Pic != null && sim2Pic != null) {

            // if differenceImg is not initialized, initialize it
            if (overlayImg == null) {
                overlayImg = new BufferedImage(sim1Pic.getWidth(), sim2Pic.getHeight(), BufferedImage.TYPE_INT_RGB);
            }
            overlayImg = alphaChannelBlending(sim1Pic, sim2Pic);

            // calculate and display difference
            picture.setIcon(new ImageIcon(overlayImg));
        }

    }

    /**
     * Overlay the two images on top of each other
     * @param img1 sim1 BufferedImage
     * @param img2 sim2 BufferedImage
     * @return blended BufferedImage
     */
    private BufferedImage alphaChannelBlending(BufferedImage img1, BufferedImage img2) {

        // get size
        int width = img1.getWidth();
        int height = img1.getHeight();

        // init to make it faster
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int r1, r2, r3, g1, g2, g3, b1, b2, b3;
        Color blendedColor;

        // Blend
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r1 = img1.getRaster().getPixel(i, j, new int[3])[0];
                r2 = img2.getRaster().getPixel(i, j, new int[3])[0];
                g1 = img1.getRaster().getPixel(i, j, new int[3])[1];
                g2 = img2.getRaster().getPixel(i, j, new int[3])[1];
                b1 = img1.getRaster().getPixel(i, j, new int[3])[2];
                b2 = img2.getRaster().getPixel(i, j, new int[3])[2];

                r3 = blend(r1, r2);
                g3 = blend(g1, g2);
                b3 = blend(b1, b2);

                // set results image
                blendedColor = new Color(r3, g3, b3);
                result.setRGB(i, j, blendedColor.getRGB());
            }
        }

        return result;

    }

    private int blend(int i, int j) {
        return Math.round(alpha * i + (1 - alpha) * j);
    }

    // setters

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    // getters

    public float getAlpha() {
        return alpha;
    }
}
