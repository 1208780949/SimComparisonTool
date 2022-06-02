package main;

import java.io.File;
import java.util.Set;

public class SettingsIO {

    private final File settingsFile;
    private static final String SETTINGS_FILE_NAME = "Settings.sct";

    public SettingsIO() {

        settingsFile = new File(System.getenv("LOCALAPPDATA") + File.separator + SETTINGS_FILE_NAME);

        // create settings file if it doesn't exist
        if (settingsFile.exists()) {
            createSettingsFile();
        }
    }

    public void createSettingsFile() {

    }

}
