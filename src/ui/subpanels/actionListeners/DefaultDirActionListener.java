package ui.subpanels.actionListeners;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefaultDirActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // variables
        String defaultDir = SimComparisonTool.settingsIO.getSetting(SettingsKeys.DEFAULT_DIRECTORY);
        String initialDir = defaultDir == null ? System.getProperty("user.dir") : defaultDir;

        // file chooser
        JFileChooser fc = new JFileChooser(initialDir);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = fc.showOpenDialog(SimComparisonTool.dispFrame);
        if (choice == JFileChooser.APPROVE_OPTION) {
            defaultDir = fc.getSelectedFile().getPath();
        }

        // set default directory
        SimComparisonTool.settingsIO.addSetting(SettingsKeys.DEFAULT_DIRECTORY, defaultDir);

        // update sims
        SimComparisonTool.sim1.newDefaultDirectory();
        SimComparisonTool.sim2.newDefaultDirectory();
    }

}
