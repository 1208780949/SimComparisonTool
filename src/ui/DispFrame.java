package ui;

import main.SimComparisonTool;
import ui.subpanels.actionListeners.ChangePictureActionListener;
import ui.subpanels.actionListeners.DefaultDirActionListener;
import ui.subpanels.actionListeners.PositionChangeActionListener;

import javax.swing.*;
import java.awt.*;
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
        initMenuBar();
        this.addKeyListener();
        setVisible(true);

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

        // 2d position menu
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
        qualityMenu.add(new JMenuItem("High"));
        qualityMenu.add(new JMenuItem("Medium"));
        qualityMenu.add(new JMenuItem("Low (Default)"));
        view.add(qualityMenu);

        return view;
    }

    /**
     * Add key listener to every component so no matter
     * where the focus it, it will always respond
     */
    private void addKeyListener() {
        KeyboardScrollListener listener = new KeyboardScrollListener();

        for (Component c : this.getRootPane().getComponents()) {
            c.addKeyListener(listener);
        }
    }

    /**
     * Updates the list of possible positions to select from when
     * displayer or scene is updated
     * @return new position
     */
    public String updatePosition() {

        String[] files = SimComparisonTool.getFilesInDisplayer();

        // don't continue if it's null
        if (files == null) {
            return null;
        }

        // initialize positions array
        String[] positions = new String[files.length];

        // sort by file name
        sortedFileNames = Arrays.stream(files).sorted().toList();

        // extract position
        for (int i = 0; i < files.length; i++) {
            String[] underscoreSplit = sortedFileNames.get(i).split("_");
            if (SimComparisonTool.is2D) {
                // 2D
                positions[i] = underscoreSplit[0];
            } else {
                // 3D
                String[] dotSplit = underscoreSplit[1].split("\\.");
                positions[i] = dotSplit[0];
            }
        }

        return (String) JOptionPane.showInputDialog(this, "Select position: ", "Position Selection", JOptionPane.PLAIN_MESSAGE, null, positions, "000.00");

    }

    // getters

    public List<String> getSortedFileNames() {
        return sortedFileNames;
    }
}
