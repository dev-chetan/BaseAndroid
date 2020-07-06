package com.android.base;

import android.app.Application;
import android.content.res.Resources;

import com.android.rb.AppConfig;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        //Don't Remove Function, For Gallery
        //Must be required
        AppConfig.getInstance().setApplicationId(BuildConfig.APPLICATION_ID);


        //GetScreen Width and Height
        int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}