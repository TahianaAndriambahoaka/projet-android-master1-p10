package com.example.tourisme_1076_1146.controleur;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePreference {
    private static final String SHARED_PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME_MODE = "theme_mode";

    public enum ThemeMode {
        LIGHT,
        DARK
    }

    public static void saveThemeMode(Context context, ThemeMode themeMode) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_THEME_MODE, themeMode.name());
        editor.apply();
    }

    public static String getThemeMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String themeModeName = prefs.getString(KEY_THEME_MODE, ThemeMode.LIGHT.name());
        return themeModeName;
    }
}
