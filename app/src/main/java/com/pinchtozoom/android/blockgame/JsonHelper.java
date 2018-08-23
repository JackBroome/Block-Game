package com.pinchtozoom.android.blockgame;

import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {

    public static String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = AppDelegate.getAppContext().getAssets().open("Level_1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
