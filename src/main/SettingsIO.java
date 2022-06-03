package main;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class SettingsIO {

    // File directories
    private final File settingsFile;
    private final String SCT_FOLDER_DIR;
    private final String SETTINGS_FILE_DIR;

    // names
    private static final String SETTINGS_FILE_NAME = "Settings.txt";
    private static final String SCT_FOLDER_NAME = "SimComparisonTool";

    //s settings
    Properties settings;

    public SettingsIO() {

        SCT_FOLDER_DIR = System.getenv("LOCALAPPDATA") + File.separator + SCT_FOLDER_NAME;
        SETTINGS_FILE_DIR = SCT_FOLDER_DIR + File.separator + SETTINGS_FILE_NAME;
        settingsFile = new File(SETTINGS_FILE_DIR);
        settings = new Properties();

        // create settings file if it doesn't exist
        if (!settingsFile.exists()) {
            createSettingsFile();
        } else {
            readPropertiesFile();
        }
    }

    /**
     * Load the properties file
     */
    private void readPropertiesFile() {

        try (InputStream input = new FileInputStream(SETTINGS_FILE_DIR)) {
            settings.load(input);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(
                    null,
                    "Settings File Load Error! Bug Chenkai about it.",
                    "File Loading Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            e.printStackTrace();
        }

    }

    /**
     * Create settings file if one does not exist already
     * It also creates the SimComparisonTool folder in
     * %localappdata% if does not exist.
     */
    private void createSettingsFile() {

        // make SCT directory if it does not exist
        File settingsDir = new File(SCT_FOLDER_DIR);
        if (!settingsDir.exists())
            //noinspection ResultOfMethodCallIgnored
            settingsDir.mkdir();

        // make file
        try {
            FileWriter fw = new FileWriter(SETTINGS_FILE_DIR);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add settings to the properties object.
     * @param key key
     * @param content content
     */
    public void addSetting(SettingsKeys key, String content) {

        settings.put(key.toString(), content);

        try {
            writePropertiesFile();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(
                    null,
                    "Settings File Output Error! Bug Chenkai about it.",
                    "File Output Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            e.printStackTrace();
        }

    }

    /**
     * Writes the properties file
     */
    private void writePropertiesFile() throws IOException {

        OutputStream output = new FileOutputStream(SETTINGS_FILE_DIR);
        settings.store(output, null);

    }

    /**
     * Get the setting based on key. If the key does not exist,
     * it returns null
     * @param key key
     * @return setting
     */
    public String getSetting(SettingsKeys key) {

        if (settings.containsKey(key.toString())) {
            return settings.getProperty(key.toString());
        } else {
            return null;
        }

    }


}
