package ui.subpanels.actionListeners;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifferencePaneDefaultAutoUpdateListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        String[] opts = {"true", "false"};
        int choice = JOptionPane.showConfirmDialog(
            SimComparisonTool.dispFrame,
            "Do you want to turn on auto update for difference pane by default?",
            "Settings",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice == 0) {
            SimComparisonTool.settingsIO.addSetting(SettingsKeys.DIFFERENCE_PANE_AUTO_UPDATE, "true");
        } else if (choice == 1) {
            SimComparisonTool.settingsIO.addSetting(SettingsKeys.DIFFERENCE_PANE_AUTO_UPDATE, "false");
        }

    }
}
