package main;

import sim.Sim;
import ui.DispFrame;

public class SimComparisonTool {

    public static DispFrame dispFrame;
    public static SettingsIO settingsIO;
    public static Sim sim1;
    public static Sim sim2;

    // sim displayer parameter
    // doing it here to ensure the two sims have the same picture displayed
    public static String displayerName;
    public static String viewName;
    public static String position;
    public static String scene;

    public static void main(String[] args) {

        // default views
        displayerName = "Total Pressure";
        viewName = "TopBottom";
        position = "001.60";
        scene = "2D";

        // initialize settings
        settingsIO = new SettingsIO();

        // initialize sims
        sim1 = new Sim();
        sim2 = new Sim();

        // initialize display frame
        dispFrame = new DispFrame();

    }

}
