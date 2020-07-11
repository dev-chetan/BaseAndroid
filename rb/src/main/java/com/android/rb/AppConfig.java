package com.android.rb;

import android.content.Context;

import com.android.rb.helper.Preferences;
import com.google.gson.Gson;

public class AppConfig {
    private static AppConfig instance;
    private String APPLICATION_ID = "";
    private int profilePlaceholder = R.drawable.img_blank_profile;
    private int noImagePlaceholder = R.drawable.img_no_image;
    private int txtPageNotFound = R.string.txt_page_not_found;
    private int colorPrimary = android.R.color.white;
    private int colorBackground = android.R.color.white;

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    private String APP_NAME = "AppName";

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME;
    }

    public int getTxtPageNotFound() {
        return txtPageNotFound;
    }

    public void setTxtPageNotFound(int txtPageNotFound) {
        this.txtPageNotFound = txtPageNotFound;
    }

    public int getProfilePlaceholder() {
        return profilePlaceholder;
    }

    public void setProfilePlaceholder(int profilePlaceholder) {
        this.profilePlaceholder = profilePlaceholder;
    }

    public int getNoImagePlaceholder() {
        return noImagePlaceholder;
    }

    public void setNoImagePlaceholder(int noImagePlaceholder) {
        this.noImagePlaceholder = noImagePlaceholder;
    }

    public String getApplicationId() {
        return APPLICATION_ID;
    }

    public void setApplicationId(String applicationId) {
        APPLICATION_ID = applicationId;
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public <T> void setAuth(Context context, T cls) {
        String s = new Gson().toJson(cls);
        Preferences.setValue(context, Preferences.USER_DATA, s);
    }
}