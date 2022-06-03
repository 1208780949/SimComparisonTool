package ui;

import main.SettingsIO;
import main.SettingsKeys;
import main.SimComparisonTool;
import ui.subpanels.actionListeners.DefaultDirActionListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Set;

public class DispFrame extends JFrame {

    public DispFrame() {
        super("fsaeSTAREvo Sim Comparison Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(400, 200));
        setContentPane(new DispPane());
        initMenuBar();
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
        scenes2DMenu.add(new JMenuItem("Pressure Coefficient"));
        scenes2DMenu.add(new JMenuItem("Total Pressure Coefficient"));
        scenes2DMenu.add(new JMenuItem("Velocity Glyph"));
        scenes2DMenu.add(new JMenuItem("Flow Angularity"));
        scenes2DMenu.add(new JMenuItem("Vorticity"));
        view.add(scenes2DMenu);

        // 2d views menu
        JMenu viewsMenu = new JMenu("2D views");
        viewsMenu.add(new JMenuItem("AftFore"));
        viewsMenu.add(new JMenuItem("Profile"));
        viewsMenu.add(new JMenuItem("TopBottom"));
        view.add(viewsMenu);

        // 2d position menu
        JMenuItem position = new JMenuItem("Set 2D position");
        view.add(position);

        // 3d scenes menu
        JMenu scene3DMenu = new JMenu("3D scenes");
        scene3DMenu.add(new JMenuItem("Pressure Coefficient"));
        scene3DMenu.add(new JMenuItem("WSS Flow Separation Indicator"));
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

}
