package com.android.rb.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.rb.AppConfig;


public class Preferences {

    public static final String APP_PREFERENCES = AppConfig.getInstance().getApplicationId();
    public static final String USER_DATA = "USER_DATA";

    public static void setValue(Context context, String Key, String Value) {
        SharedPreferences settings = context.getSharedPreferences(
                APP_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Key, Value);
        editor.apply();
    }

    public static void setIntValue(Context context, String Key, int Value) {
        SharedPreferences settings = context.getSharedPreferences(
                APP_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Key, Value);
        editor.apply();
    }

    public static boolean getValueBoolean(Context context, String Key, boolean Default) {
        if (context != null) {
            SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, 0);
            return settings.getBoolean(Key, Default);
        } else {
            return false;
        }
    }

    public static String getValueString(Context context, String Key) {
        SharedPreferences settings = context.getSharedPreferences(
                APP_PREFERENCES, 0);
        return settings.getString(Key, "");
    }

    public static int getValueInt(Context context, String Key, int Default) {
        if (context != null) {
            SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, 0);
            return settings.getInt(Key, Default);
        } else {
            return 0;
        }
    }

    public static void setValue(Context context, String Key, boolean Value) {
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Key, Value);
        editor.apply();
    }

    public static void clearAppPreferences(Context context) {
        SharedPreferences settings = context.getSharedPreferences(APP_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }
}
