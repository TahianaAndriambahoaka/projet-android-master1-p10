package com.example.tourisme_1076_1146.vue.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.LanguagePreference;
import com.example.tourisme_1076_1146.controleur.ThemePreference;
import com.example.tourisme_1076_1146.vue.fragments.InscriptionFragment;
import com.example.tourisme_1076_1146.vue.fragments.LoginFragment;

import java.util.Locale;

public class LoginInscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String savedLanguage = LanguagePreference.getLanguage(this);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(new Locale(savedLanguage));
        resources.updateConfiguration(configuration, displayMetrics);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inscription);

        String themeModeName = ThemePreference.getThemeMode(this);
        if (themeModeName.equals("DARK")) {
            findViewById(R.id.light).setVisibility(View.GONE);
            findViewById(R.id.dark).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.light).setVisibility(View.VISIBLE);
            findViewById(R.id.dark).setVisibility(View.GONE);
        }

        LoginFragment loginFragment = new LoginFragment();
        InscriptionFragment inscriptionFragment = new InscriptionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.card, loginFragment);
        fragmentTransaction.addToBackStack(null); // Pour permettre le retour en arrière avec le bouton de retour
        fragmentTransaction.commit();
    }
}