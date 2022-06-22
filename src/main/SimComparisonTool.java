package main;

import sim.Sim;
import ui.DispFrame;
import ui.subpanels.SimDisplayPanel;
import ui.subpanels.Subpanel;

import java.io.File;

public class SimComparisonTool {

    public static DispFrame dispFrame;
    public static SettingsIO settingsIO;
    public static Sim sim1;
    public static Sim sim2;
    public static final String SCT_FOLDER_NAME = "SimComparisonTool";

    // sim displayer parameter
    // doing it here to ensure the two sims have the same picture displayed
    public static String displayerName;
    public static String viewName;
    public static String position;
    public static boolean is2D;

    public static void main(String[] args) {

        // default views
        displayerName = "Total Pressure";
        viewName = "TopBottom";
        position = "001.60";
        is2D = true;

        // initialize settings
        settingsIO = new SettingsIO();

        // initialize sims
        sim1 = new Sim();
        sim2 = new Sim();

        // initialize display frame
        dispFrame = new DispFrame();

        // show default pictures
        for (int i = 0; i < 4; i++) {
            ((Subpanel) dispFrame.getContentPane().getComponent(i)).showDefaultPic();
        }
    }

    /**
     * Get all files names in a displayer. If the directory does
     * not exist, return null.
     * @return directories all files
     */
    public static String[] getFilesInDisplayer() {

        // using sim1

        // 2D case
        File displayerDir;
        if (SimComparisonTool.is2D) {
            displayerDir = new File(sim1.getSimDir() + File.separator + sim1.getFolderDirectory(SimComparisonTool.displayerName, SimComparisonTool.viewName));
        } else {
            // 3D
            displayerDir = new File(sim1.getSimDir() + File.separator + sim1.getFolderDirectory(SimComparisonTool.displayerName));
        }
        if (displayerDir.exists()) {
            return displayerDir.list();
        } else {
            return null;
        }


    }

}
