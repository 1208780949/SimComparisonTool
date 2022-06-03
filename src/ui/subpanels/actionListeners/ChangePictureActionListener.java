package ui.subpanels.actionListeners;

import main.SimComparisonTool;
import ui.DispPane;

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
        String scene = is2D ? "2D" : "3D";
        if (!SimComparisonTool.scene.equals(scene)) {
            SimComparisonTool.scene = scene;
        }

        // display new picture
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).getSimDisplayPanelLeft().updatePicture();
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).getSimDisplayPanelRight().updatePicture();

    }

}
