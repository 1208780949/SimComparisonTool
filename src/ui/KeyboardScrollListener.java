package ui;

import main.SimComparisonTool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class KeyboardScrollListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        // intentionally left blank
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            // left
            scrollPic(false);
        } else if (e.getKeyCode() == 39) {
            // right
            scrollPic(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // intentionally left blank
    }

    /**
     * Scroll through pictures
     * @param isNext true for next pic
     */
    private void scrollPic(boolean isNext) {

        // figure out the index of this picture
        List<String> sortedFileNames = SimComparisonTool.dispFrame.getSortedFileNames(false);
        int i = sortedFileNames.indexOf(SimComparisonTool.sim1.getFileName(SimComparisonTool.position, SimComparisonTool.displayerName, SimComparisonTool.is2D));
        if (i == -1) {
            // if the file can't be found, default to first picture
            i = 0;
        }

        // figure out the index of the picture to scroll to
        if (isNext) {
            if (i == sortedFileNames.size() - 1) {
                i = 0;
            } else {
                i++;
            }
        } else {
            if (i == 0) {
                i = sortedFileNames.size() - 1;
            } else {
                i--;
            }
        }

        // update position
        String[] underscoreSplit = sortedFileNames.get(i).split("_");
        if (SimComparisonTool.is2D) {
            // 2D
            SimComparisonTool.position = underscoreSplit[0];
        } else {
            // 3D
            // since some 3D scenes have 2 underscores and some have 1,
            // the program needs to check that and get the correct split
            String correctedSplit;
            if (underscoreSplit.length == 3) {
                correctedSplit = underscoreSplit[1] + "_" + underscoreSplit[2];
            } else {
                correctedSplit = underscoreSplit[1];
            }

            String[] dotSplit = correctedSplit.split("\\.");
            SimComparisonTool.position = dotSplit[0];
        }

        // update pictures
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).updatePictures();

    }
}
