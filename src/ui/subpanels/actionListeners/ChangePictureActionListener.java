package ui.subpanels.actionListeners;

import main.SimComparisonTool;
import sim.Sim;
import ui.DispPane;
import ui.subpanels.Subpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record ChangePictureActionListener(Parameter change, String newValue, boolean is2D) implements ActionListener {

    public enum Parameter {
        DISPLAYER,
        VIEW,
        POSITION,
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // change view parameters in SimComparisonTool
        switch (change) {
            case DISPLAYER -> SimComparisonTool.displayerName = newValue;
            case VIEW -> SimComparisonTool.viewName = newValue;
            case POSITION -> SimComparisonTool.position = newValue;
        }

        // make sure position is good when switching scenes
        // make sure the files in the new folder are sorted when switching scenes
        if (SimComparisonTool.is2D && !is2D) {
            SimComparisonTool.position = "[3D] [FW] Back";
            SimComparisonTool.dispFrame.getSortedFileNames(true);
        } else if (!SimComparisonTool.is2D && is2D) {
            SimComparisonTool.position = "000.00";
            SimComparisonTool.dispFrame.getSortedFileNames(true);
        }
        SimComparisonTool.is2D = is2D;

        // make sure the files in the new folder are sorted when switching displayer or view
        if (change.equals(Parameter.DISPLAYER) || change.equals(Parameter.VIEW)) {
            SimComparisonTool.dispFrame.getSortedFileNames(true);
        }

        // display new picture
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).updatePictures();

    }

}
