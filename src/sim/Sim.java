package sim;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Sim {

    private String simDir;
    private String simName;
    private boolean isValid; // whether this is a valid sim results location or not
    BufferedImage picture;
    BufferedImage resizedCopy;
    JFileChooser fc; // I'm initializing this in constructor because JFileChooser is extremely slow to initialize
    private ArrayList<String> report;

    // which picture is it

    public Sim() {
        String defaultDirectory = SimComparisonTool.settingsIO.getSetting(SettingsKeys.DEFAULT_DIRECTORY);
        simDir = defaultDirectory == null ? System.getProperty("user.dir") : defaultDirectory;
        report = new ArrayList<>();
        fc = new JFileChooser(simDir);
    }

    /**
     * When a SimDisplayPanel is clicked when in focus,
     * this method will be called.
     * This method allows the user to select a new sim
     * to view.
     */
    public void newSim() {

        // show file browser
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = fc.showOpenDialog(SimComparisonTool.dispFrame);
        if (choice == JFileChooser.APPROVE_OPTION) {
            simDir = fc.getSelectedFile().getPath();
            isValid = checkValidity();
        }

        // show picture if isValid
        if (isValid) {
            String[] simDirFolders = simDir.split("\\\\"); // 4 backslashes are necessary
            simName = simDirFolders[simDirFolders.length - 1];
            readReport();
            showPicture();
        }

    }

    /**
     * Called when a new picture needs to be displayed
     */
    public void newPicture() {

        // check view
        // if the view does not exist, go to 000.00
        File newPic = new File(getFileDirectory());
        if (!newPic.exists()) {
            SimComparisonTool.position = "000.00";
        }

        // show the picture
        showPicture();

    }

    /**
     * Check whether or the folder chosen is a valid sim file location by checking
     * for the existence of 2D scenes and 3D scenes folder
     */
    private boolean checkValidity() {

        File scenes2D = new File(simDir + File.separator + "Plots");
        File scenes3D = new File(simDir + File.separator + "Reports");
        if (!(scenes2D.exists() && scenes3D.exists())) {
            JOptionPane.showConfirmDialog(null, "Sim chosen is invalid", "Invalid Sim", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Shows picture at location defined in SimComparisonTool.
     * Also updates the title of dispFrame to give user info
     * about the pictures.
     */
    private void showPicture() {

        try {
            picture = ImageIO.read(new File(getFileDirectory()));
            SimComparisonTool.dispFrame.setTitle(SimComparisonTool.displayerName + " " + SimComparisonTool.position + " " + SimComparisonTool.viewName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Update default directory if a new one has been set if
     * and only if no sim is displayed
     */
    public void newDefaultDirectory() {

        if (picture == null) {
            simDir = SimComparisonTool.settingsIO.getSetting(SettingsKeys.DEFAULT_DIRECTORY);
        }

    }

    /**
     * Construct the directory of the picture file
     * @return directory of the picture
     */
    public String getFileDirectory() {
        if (SimComparisonTool.is2D) {
            return simDir + File.separator + getFolderDirectory(SimComparisonTool.displayerName, SimComparisonTool.viewName) + File.separator + getFileName(SimComparisonTool.position, SimComparisonTool.displayerName, true);
        } else {
            return simDir + File.separator + getFolderDirectory(SimComparisonTool.displayerName) + File.separator + getFileName(SimComparisonTool.position, SimComparisonTool.displayerName, false);
        }
    }

    // reports

    /**
     * Read report upon opening a new sim
     */
    private void readReport() {

        try (BufferedReader br = new BufferedReader(new FileReader(simDir + File.separator + "Reports" + File.separator + "Reports.csv"))) {

            int lineNum = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (lineNum == 1) {
                    // only read the second line
                    String[] values = line.split(",");
                    report.addAll(Arrays.asList(values));
                } else {
                    lineNum++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // file name manipulation

    /**
     * 2D and 3D integrated version of getFileName.
     * @param displayerName name of the displayer in STAR
     * @return file name
     */
    public String getFileName(String position, String displayerName, boolean is2D) {
        if (is2D) {
            return position + "_" + displayerName + ".png";
        } else {
            return displayerName + "_" + position + ".png";
        }

    }

    /**
     * 2D version of getFolderDirectory.
     * Gets the subdirectory from the sim directory
     * @param displayerName displayer name
     * @param viewName view name
     * @return folder subdirectory
     */
    public String getFolderDirectory(String displayerName, String viewName) {
        return "2D scenes" + File.separator + displayerName + " [2D] [" + viewName + "] Car";
    }

    /**
     * 3D version of getFolderDirectory.
     * Gets the subdirectory from the sim directory
     * @param displayerName displayer name
     * @return folder subdirectory
     */
    public String getFolderDirectory(String displayerName) {
        return "3D scenes" + File.separator + displayerName;
    }

    // setters

    public void setResizedCopy(BufferedImage resizedCopy) {
        this.resizedCopy = resizedCopy;
    }

    // getter

    public boolean isValid() {
        return isValid;
    }

    public BufferedImage getPicture(){
        return picture;
    }

    public String getSimDir() {
        return simDir;
    }

    public BufferedImage getResizedCopy() {
        return resizedCopy;
    }

    public String getSimName() {
        return simName;
    }

    public String getReportValueAt(int i) {
        return report.get(i);
    }
}
