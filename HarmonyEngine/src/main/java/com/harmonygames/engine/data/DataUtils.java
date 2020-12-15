package com.harmonygames.engine.data;

import java.io.File;

public class DataUtils {

    private static File saveLocation;

    public static File getSaveLocation() { return DataUtils.saveLocation; }

    public static void setSaveLocation(String gameTitle) {
        File saveLocation = new File(System.getProperty("user.home") + File.separator + "." + gameTitle);
        if(!saveLocation.exists()) {
            boolean status = saveLocation.mkdirs();
            if(!status) {
                System.err.println("[Harmony Engine (DataUtils)]: Could not save to the save location: " + saveLocation.getPath());
                System.exit(-1);
            }
        }

        DataUtils.saveLocation = saveLocation;
    }

}