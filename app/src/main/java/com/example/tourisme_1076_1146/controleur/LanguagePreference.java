package com.example.tourisme_1076_1146.controleur;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class LanguagePreference {
    private static final String KEY_LANGUAGE_CODE = "fr";

    public static void saveLanguage(Context context, String languageCode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE_CODE, languageCode);
        editor.apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY_LANGUAGE_CODE, "fr");
    }
}
