package ui.subpanels.actionListeners;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverlayPaneDefaultAutoUpdateListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        int choice = JOptionPane.showConfirmDialog(
                SimComparisonTool.dispFrame,
                "Do you want to turn on auto update for overlay pane by default?",
                "Settings",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice == 0) {
            SimComparisonTool.settingsIO.addSetting(SettingsKeys.OVERLAY_PANE_AUTO_UPDATE, "true");
        } else if (choice == 1) {
            SimComparisonTool.settingsIO.addSetting(SettingsKeys.OVERLAY_PANE_AUTO_UPDATE, "false");
        }

    }
}
