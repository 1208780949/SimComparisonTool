package ui.subpanels;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class DifferencePanel extends Subpanel {


    private BufferedImage differenceImg;
    private final JLabel picture;
    private boolean autoUpdate;

    public DifferencePanel() {
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setVerticalAlignment(JLabel.CENTER);

        // auto update
        autoUpdate = SimComparisonTool.settingsIO.getBoolSetting(SettingsKeys.DIFFERENCE_PANE_AUTO_UPDATE);

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

            // custom grayscale conversion
            BufferedImage gs1 = customGrayscale(sim1Pic);
            BufferedImage gs2 = customGrayscale(sim2Pic);

            // if differenceImg is not initialized, initialize it
            if (differenceImg == null) {
                differenceImg = new BufferedImage(gs1.getWidth(), gs1.getHeight(), BufferedImage.TYPE_INT_RGB);
            }

            // calculate and display difference
            differenceImg = imageSubtract(gs1, gs2);
            picture.setIcon(new ImageIcon(differenceImg));
        }
    }

    /**
     * Okay so there is a LOT to talk about here.
     * The reason that a custom grayscale conversion function exists is that
     * the Turbo colormap (the colormap we use for CFD) is very weird. The RGB
     * values go up and down all over the place. If I were to use a standard
     * grayscale conversion, the areas of extremely red and extremely blue
     * would look exactly the same. I can't look at a single RGB value either
     * because they are not high at one end and low at the other. As far as I
     * am aware, no polynomial up to 6 degrees fit the blue and red curves to
     * any degree to accuracy. A 6 degree curve would fit the green curve, but
     * good luck finding analytical solution to that. I am not using numerical
     * root finding because it's too slow for this purpose.
     * So after probably over 5 hours of fuckery in Excel, I came up with two
     * functions are would produce a curve that I could work with, which is
     * any curve that always go up or down because only on mathematical
     * variants of RGB vs. position graphs. The problem is, the function is
     * not linear, so there are places where the rate of change of grayscaled
     * picture does not match the original picture, and that the grayscaled
     * picture is nowhere near a perfect representation of the position of
     * the RGB value on the colormap. So if you want to improve on this, you
     * can totally do that.
     * You might be saying why don't you just create a map that contains RGB
     * values and their positions on the colormap. Well, that's simply too
     * slow. Because you need to look through 256 points for every pixel. And
     * they would not be exact match, so you need to do more calculations for
     * approximate match. That is way too slow.
     * @param img image
     * @return custom grayscale image
     */
    private BufferedImage customGrayscale(BufferedImage img) {

        // get size of picture
        int width = img.getWidth();
        int height = img.getHeight();

        // initialize
        // Doing this outside of the loop to make it faster
        int[] color = new int[3];
        float e;
        float f;
        float used;
        Color pixel;
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                color = img.getRaster().getPixel(i, j, new int[3]);

                // random calculations that I came up with on Excel
                // that converts RGB to position on color map.
                // The result is no way near linear, it's actually
                // closer to vertically offset exponential function
                // than anything else. So keep that in mind.
                e = (float) color[2] / color[1] * 3;
                f = (float) ((float) color[2] / color[0] - 1.5);
                if (e > f && color[2] > color[0] && color[2] > color[1]) {
                    used = e;
                } else if (e > f && (color[2] < color[0] || color[2] < color[1])) {
                    used = f;
                } else {
                    used = f;
                }
                used = (float) ((Math.pow(100, (1 - ((used + 1.47) / 11.1761036789298))) - 1) / 98.8761852602268);

                // I SUSPECT (keyword: suspect) what is happening is that
                // because the texts are black, the algorithm above does
                // not know what to with it because it's outside of the
                // designed color range. So it just happens to output
                // negative numbers. This is just here to negate that issue.
                if (used < 0) {
                    used = 0;
                } else if (used > 1) {
                    used = 1;
                }

                // set grayscale
                pixel = new Color(used, used ,used);
                newImg.setRGB(i, j, pixel.getRGB());

            }
        }

        return newImg;

    }

    private BufferedImage imageSubtract(BufferedImage img1, BufferedImage img2) {

        // get size
        int width = img1.getWidth();
        int height = img1.getHeight();

        // init
        // Again, to make it faster
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        float diff;
        Color diffColor;

        // subtract
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                diff = ((float) (img1.getRaster().getPixel(i, j, new int[3])[0] / 255.0)) - ((float) (img2.getRaster().getPixel(i, j, new int[3])[0] / 255.0));
                diff += 1;
                diff /= 2;

                diffColor = new Color(diff, diff, diff);
                result.setRGB(i, j, diffColor.getRGB());

            }
        }

        return result;

    }

    // settings

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }
}
