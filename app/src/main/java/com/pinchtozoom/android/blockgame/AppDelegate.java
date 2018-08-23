package com.pinchtozoom.android.blockgame;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class AppDelegate extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AppDelegate", "AppDelegate Launched");
        appContext = this;
    }
    public static Context getAppContext() {
        return appContext;
    }
}