package ui;

import main.SimComparisonTool;
import ui.reports.ReportsFrame;
import ui.subpanels.actionListeners.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DispFrame extends JFrame {

    private java.util.List<String> sortedFileNames;

    public DispFrame() {
        super("fsaeSTAREvo Sim Comparison Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(400, 200));
        setContentPane(new DispPane());
        initIcon();
        initMenuBar();
        this.addKeyListener();
        setVisible(true);

    }

    /**
     * Initializes the icon for disp frame
     */
    private void initIcon() {

        try {
            Image icon = ImageIO.read(new File(System.getenv("LOCALAPPDATA") + File.separator + SimComparisonTool.SCT_FOLDER_NAME + File.separator + "ICON"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates menu bar as well as everything in it by calling
     * other methods
     */
    private void initMenuBar() {

        // initialize menu bar
        JMenuBar menuBar = new JMenuBar();

        // add menus
        menuBar.add(initFileMenu());
        menuBar.add(initViewMenu());
        menuBar.add(initDifferenceMenu());
        menuBar.add(initOverlayMenu());
        menuBar.add(initReportsMenu());

        // add menu bar to ui.DispFrame
        setJMenuBar(menuBar);
    }

    /**
     * Creates the file drop down menu
     * @return file menu
     */
    private JMenu initFileMenu() {

        JMenu file = new JMenu("File");

        JMenuItem defaultDir = new JMenuItem("Default Directory");
        defaultDir.addActionListener(new DefaultDirActionListener());
        file.add(defaultDir);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);

        return file;

    }

    /**
     * Add settings menu which contents options for user to select
     * scenes and view to look at
     * @return settings menu
     */
    private JMenu initViewMenu() {

        JMenu view = new JMenu("View");

        // 2d scenes
        JMenu scenes2DMenu = new JMenu("2D scenes");
        JMenuItem cp2d = new JMenuItem("Pressure Coefficient");
        JMenuItem cpt2d = new JMenuItem("Total Pressure Coefficient");
        JMenuItem vel2d = new JMenuItem("Velocity Glyph");
        JMenuItem fa2d = new JMenuItem("Flow Angularity");
        JMenuItem vorticity2d = new JMenuItem("Vorticity");
        cp2d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "Pressure", true));
        cpt2d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "Total Pressure", true));
        vel2d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "Velocity Glyph", true));
        fa2d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "Flow Angularity", true));
        vorticity2d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "Vorticity", true));
        scenes2DMenu.add(cp2d);
        scenes2DMenu.add(cpt2d);
        scenes2DMenu.add(vel2d);
        scenes2DMenu.add(fa2d);
        scenes2DMenu.add(vorticity2d);
        view.add(scenes2DMenu);

        // 2d views menu
        JMenu viewsMenu = new JMenu("2D views");
        JMenuItem aftFore = new JMenuItem("AftFore");
        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem topBottom = new JMenuItem("TopBottom");
        aftFore.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.VIEW, "AftFore", true));
        profile.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.VIEW, "Profile", true));
        topBottom.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.VIEW, "TopBottom", true));
        viewsMenu.add(aftFore);
        viewsMenu.add(profile);
        viewsMenu.add(topBottom);
        view.add(viewsMenu);

        // position menu
        JMenuItem positionMenu = new JMenuItem("Set Position");
        positionMenu.addActionListener(new PositionChangeActionListener());
        view.add(positionMenu);

        // 3d scenes menu
        JMenu scene3DMenu = new JMenu("3D scenes");
        JMenuItem cp3d = new JMenuItem("Pressure Coefficient");
        JMenuItem wss3d = new JMenuItem("WSS Flow Separation Indicator");
        cp3d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "Pressure", false));
        wss3d.addActionListener(new ChangePictureActionListener(ChangePictureActionListener.Parameter.DISPLAYER, "WSS Separation Indicator", false));
        scene3DMenu.add(cp3d);
        scene3DMenu.add(wss3d);
        view.add(scene3DMenu);

        view.addSeparator();

        // image quality menu
        JMenu qualityMenu = new JMenu("Image Quality");
        JMenuItem highQuality = new JMenuItem("High");
        JMenuItem mediumQuality = new JMenuItem("Medium");
        JMenuItem lowQuality = new JMenuItem("Low (Default)");
        highQuality.addActionListener(e -> ((DispPane) SimComparisonTool.dispFrame.getContentPane()).setImageQuality(Image.SCALE_SMOOTH));
        mediumQuality.addActionListener(e -> ((DispPane) SimComparisonTool.dispFrame.getContentPane()).setImageQuality(Image.SCALE_DEFAULT));
        lowQuality.addActionListener(e -> ((DispPane) SimComparisonTool.dispFrame.getContentPane()).setImageQuality(Image.SCALE_FAST));
        qualityMenu.add(highQuality);
        qualityMenu.add(mediumQuality);
        qualityMenu.add(lowQuality);
        view.add(qualityMenu);

        return view;
    }

    /**
     * Create menu for difference pane
     * @return difference menu
     */
    private JMenu initDifferenceMenu() {

        JMenu difference = new JMenu("Difference Pane");

        JMenuItem setDeadZone = new JMenuItem("Set Dead Zone (WIP)");
        difference.add(setDeadZone);

        difference.addSeparator();

        JMenuItem autoUpdateOn = new JMenuItem("Auto Update On");
        autoUpdateOn.addActionListener(e -> ((DispPane) getContentPane()).getDifferencePanel().setAutoUpdate(true));
        difference.add(autoUpdateOn);

        JMenuItem autoUpdateOff = new JMenuItem("Auto Update Off");
        autoUpdateOff.addActionListener(e -> ((DispPane) getContentPane()).getDifferencePanel().setAutoUpdate(false));
        difference.add(autoUpdateOff);

        JMenuItem autoUpdateDefault = new JMenuItem("Set Default Auto Update");
        autoUpdateDefault.addActionListener(new DifferencePaneDefaultAutoUpdateListener());
        difference.add(autoUpdateDefault);

        difference.addSeparator();

        JMenuItem highAccuracy = new JMenuItem("High Accuracy");
        highAccuracy.addActionListener(e -> ((DispPane) getContentPane()).getDifferencePanel().setAccurate(true));
        difference.add(highAccuracy);

        JMenuItem lowAccuracy = new JMenuItem("Low Accuracy (default)");
        lowAccuracy.addActionListener(e -> ((DispPane) getContentPane()).getDifferencePanel().setAccurate(false));
        difference.add(lowAccuracy);

        return difference;
    }

    /**
     * Create menu for overlay pane
     * @return overlay menu
     */
    private JMenu initOverlayMenu() {
        JMenu overlay = new JMenu("Overlay Pane");

        JMenuItem setAlpha = new JMenuItem("Set Transparency");
        setAlpha.addActionListener(new SetAlphaListener());
        overlay.add(setAlpha);

        overlay.addSeparator();

        JMenuItem autoUpdateOn = new JMenuItem("Auto Update On");
        autoUpdateOn.addActionListener(e -> ((DispPane) getContentPane()).getOverlayPanel().setAutoUpdate(true));
        overlay.add(autoUpdateOn);

        JMenuItem autoUpdateOff = new JMenuItem("Auto Update Off");
        autoUpdateOff.addActionListener(e -> ((DispPane) getContentPane()).getOverlayPanel().setAutoUpdate(false));
        overlay.add(autoUpdateOff);

        JMenuItem autoUpdateDefault = new JMenuItem("Set Default Auto Update");
        autoUpdateDefault.addActionListener(new OverlayPaneDefaultAutoUpdateListener());
        overlay.add(autoUpdateDefault);

        return overlay;
    }

    /**
     * Create reports menu that will handle everything
     * in the Reports folder
     * @return report
     */
    private JMenu initReportsMenu() {
        JMenu report = new JMenu("Reports");

        JMenuItem showReportsFile = new JMenuItem("Show Reports File");
        showReportsFile.addActionListener(e -> new ReportsFrame());
        report.add(showReportsFile);

        JMenuItem showConvergenceFile = new JMenuItem("Show Convergence File");
        report.add(showConvergenceFile);

        return report;
    }

    /**
     * Add key listener to every component so no matter
     * where the focus it, it will always respond
     */
    private void addKeyListener() {
        KeyboardScrollListener listener = new KeyboardScrollListener();

        // get all components in dispFrame and add key listener
        List<Component> allComp = getAllComponents(this);
        for (Component c : allComp) {
            c.addKeyListener(listener);
        }
    }

    /**
     * Get all components recursively.
     * Thanks, stack overflow
     * @param c container
     * @return list of all components
     */
    private List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }

    /**
     * Updates the list of possible positions to select from when
     * displayer or scene is updated
     * @return new position
     */
    public String updatePosition() {

        // sort files by name
        String[] files = SimComparisonTool.getFilesInDisplayer();
        sortFileNames(files);

        // stop if files is null
        if (files == null) {
            return null;
        }

        // initialize positions array
        String[] positions = new String[files.length];

        // extract position
        for (int i = 0; i < files.length; i++) {
            String[] underscoreSplit = sortedFileNames.get(i).split("_");
            if (SimComparisonTool.is2D) {
                // 2D
                positions[i] = underscoreSplit[0];
            } else {
                // 3D
                // since some 3D scenes have 2 underscores and some have 1,
                // the program needs to check that and get the correct split
                String correctedSplit;
                if (underscoreSplit.length == 3) {
                    correctedSplit = underscoreSplit[1] + "_" + underscoreSplit[2];
                } else {
                    correctedSplit = underscoreSplit[1];
                }

                String[] dotSplit = correctedSplit.split("\\.");
                positions[i] = dotSplit[0];
            }
        }

        return (String) JOptionPane.showInputDialog(this, "Select position: ", "Position Selection", JOptionPane.PLAIN_MESSAGE, null, positions, "000.00");

    }

    /**
     * Sorts all the files in a directory by its name
     */
    private void sortFileNames(String[] files) {

        if (files == null) {
            return;
        }

        // sort by file name
        sortedFileNames = Arrays.stream(files).sorted().toList();
    }

    // setters

    @Override
    public void setTitle(String title) {
        String sim1Name = SimComparisonTool.sim1.getSimName();
        String sim2Name = SimComparisonTool.sim2.getSimName();
        super.setTitle(title);
        if (sim1Name != null && sim2Name != null) {
            super.setTitle(super.getTitle() + " | " + SimComparisonTool.sim1.getSimName() + " | " + SimComparisonTool.sim2.getSimName());
        }

    }


    // getters

    public List<String> getSortedFileNames(boolean folderChanged) {

        // if sorted file names does not exist, create it
        if (sortedFileNames == null || folderChanged) {
            sortFileNames(SimComparisonTool.getFilesInDisplayer());
        }
        return sortedFileNames;
    }
}
