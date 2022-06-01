package sim;

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

    public Sim() {
        simDir = System.getProperty("user.dir");
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
            checkValidity();
        }

    }

    /**
     * Check whether or the folder chosen is a valid sim file location by checking
     * for the existence of 2D scenes and 3D scenes folder
     */
    private void checkValidity() {

        File scenes2D = new File(simDir + File.separator + "2D scenes");
        File scenes3D = new File(simDir + File.separator + "3D scenes");
        if (!(scenes2D.exists() && scenes3D.exists())) {
            JOptionPane.showConfirmDialog(null, "Sim chosen is invalid", "Invalid Sim", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            isValid = false;
        } else {
            isValid = true;
            setDefaultView();
        }
    }

    /**
     * Sets the default view. Which is 2D total pressure at 1.6.
     */
    private void setDefaultView() {

        String displayerName = "Total Pressure";
        String viewName = "TopBottom";
        String position = "001.60";
        try {
            String picDir = simDir + File.separator + getFolderDirectory(displayerName, viewName) + File.separator + getFileName(position, displayerName);
            picture = ImageIO.read(new File(picDir));
        } catch (IOException e) {
            e.printStackTrace();
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
