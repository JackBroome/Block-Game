package com.pinchtozoom.android.blockgame.Library;

import com.pinchtozoom.android.blockgame.AppDelegate;

import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {

    public static String loadJSONFromAsset(int level) {
        String json = null;
        try {
            InputStream is = AppDelegate.getAppContext().getAssets().open(String.format("Level_%s.json", level));
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
