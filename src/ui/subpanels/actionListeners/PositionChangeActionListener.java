package ui.subpanels.actionListeners;

import main.SimComparisonTool;
import ui.DispPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PositionChangeActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedPosition = SimComparisonTool.dispFrame.updatePosition();

        // don't do anything if the selected position is null
        if (selectedPosition == null) {
            return;
        }

        // set position
        SimComparisonTool.position = selectedPosition;

        // update picture
        ((DispPane) SimComparisonTool.dispFrame.getContentPane()).updatePictures();

    }

}
