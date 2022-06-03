package sim;

import main.SettingsKeys;
import main.SimComparisonTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sim {

    private String simDir;
    private boolean isValid; // whether this is a valid sim results location or not
    BufferedImage picture;

    // which picture is it


    public Sim() {
        String defaultDirectory = SimComparisonTool.settingsIO.getSetting(SettingsKeys.DEFAULT_DIRECTORY);
        simDir = defaultDirectory == null ? System.getProperty("user.dir") : defaultDirectory;


    }

    /**
     * When a SimDisplayPanel is clicked when in focus,
     * this method will be called.
     * This method allows the user to select a new sim
     * to view.
     */
    public void newSim() {

        // show file browser
        JFileChooser fc = new JFileChooser(simDir);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = fc.showOpenDialog(SimComparisonTool.dispFrame);
        if (choice == JFileChooser.APPROVE_OPTION) {
            simDir = fc.getSelectedFile().getPath();
            isValid = checkValidity();
        }

        // show picture if isValid
        if (isValid) {
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

        File scenes2D = new File(simDir + File.separator + "2D scenes");
        File scenes3D = new File(simDir + File.separator + "3D scenes");
        if (!(scenes2D.exists() && scenes3D.exists())) {
            JOptionPane.showConfirmDialog(null, "Sim chosen is invalid", "Invalid Sim", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets the default view. Which is 2D total pressure at 1.6.
     */
    private void showPicture() {

        try {
            picture = ImageIO.read(new File(getFileDirectory()));
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
        if (SimComparisonTool.scene.equals("2D")) {
            return simDir + File.separator + getFolderDirectory(SimComparisonTool.displayerName, SimComparisonTool.viewName) + File.separator + getFileName(SimComparisonTool.position, SimComparisonTool.displayerName);
        } else {
            return simDir + File.separator + getFolderDirectory(SimComparisonTool.displayerName) + File.separator + getFileName(SimComparisonTool.displayerName);
        }
    }

    // file name manipulation

    /**
     * 2D version of getFileName.
     * @param displayerName name of the displayer in STAR
     * @return file name
     */
    private String getFileName(String position, String displayerName) {
        return position + "_" + displayerName + ".png";
    }

    /**
     * 3D version of getFileName.
     * This will always return the name of the 3D FW Back scene
     * because the user is only allowed to scroll through 3D scenes.
     * @param displayerName name of the displayer in STAR
     * @return file name
     */
    private String getFileName(String displayerName) {
        return displayerName + "_[3D] [FW] Back.png";
    }

    /**
     * 2D version of getFolderDirectory.
     * Gets the subdirectory from the sim directory
     * @param displayerName displayer name
     * @param viewName view name
     * @return folder subdirectory
     */
    private String getFolderDirectory(String displayerName, String viewName) {
        return "2D scenes" + File.separator + displayerName + " [2D] [" + viewName + "] Car";
    }

    /**
     * 3D version of getFolderDirectory.
     * Gets the subdirectory from the sim directory
     * @param displayerName displayer name
     * @return folder subdirectory
     */
    private String getFolderDirectory(String displayerName) {
        return "3D scenes" + File.separator + displayerName;
    }

    // getter

    public boolean isValid() {
        return isValid;
    }

    public BufferedImage getPicture(){
        return picture;
    }
}
