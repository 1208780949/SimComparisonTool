package ui.subpanels.actionListeners;

import main.SimComparisonTool;
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

        // make sure scenes is good
        if (SimComparisonTool.is2D && !is2D) {
            SimComparisonTool.position = "[3D] [FW] Back";
        } else if (!SimComparisonTool.is2D && is2D) {
            SimComparisonTool.position = "000.00";
        }
        SimComparisonTool.is2D = is2D;

        // display new picture
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).updatePictures();

    }

}
