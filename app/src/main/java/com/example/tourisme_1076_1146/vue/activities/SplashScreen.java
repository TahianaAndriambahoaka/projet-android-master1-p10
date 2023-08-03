package com.example.tourisme_1076_1146.vue.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tourisme_1076_1146.controleur.LanguagePreferences;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String savedLanguage = LanguagePreferences.getLanguage(this);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(new Locale(savedLanguage));
        resources.updateConfiguration(configuration, displayMetrics);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginInscriptionActivity.class));
                finish();
            }
        }, 2000);
    }
}
