package ui;

import main.SimComparisonTool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class KeyboardScrollListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            // left
            lastPicture();
        } else if (e.getKeyCode() == 39) {
            // right
            nextPicture();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void nextPicture() {

        List<String> sortedFileNames = SimComparisonTool.dispFrame.getSortedFileNames();
        int i = sortedFileNames.indexOf(SimComparisonTool.sim1.getFileName(SimComparisonTool.position, SimComparisonTool.displayerName, SimComparisonTool.is2D));
        if (i == sortedFileNames.size() - 1) {
            i = 0;
        } else {
            i += 1;
        }

        String[] underscoreSplit = sortedFileNames.get(i).split("_");
        if (SimComparisonTool.is2D) {
            // 2D
            SimComparisonTool.position = underscoreSplit[0];
        } else {
            // 3D
            String[] dotSplit = underscoreSplit[1].split("\\.");
            SimComparisonTool.position = dotSplit[0];
        }

    }

    private void lastPicture() {

    }
}
