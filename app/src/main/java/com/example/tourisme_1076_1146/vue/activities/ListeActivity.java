package com.example.tourisme_1076_1146.vue.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.controleur.LanguagePreference;
import com.example.tourisme_1076_1146.controleur.ThemePreference;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;
import com.example.tourisme_1076_1146.vue.fragments.ActiviteTouristiqueFragment;
import com.example.tourisme_1076_1146.vue.fragments.RechercheFragment;

import java.util.List;
import java.util.Locale;

public class ListeActivity extends AppCompatActivity implements RechercheFragment.OnSearchSubmitListener {

    @Override
    public void onSearchSubmit(String query) {
        // Traitez la recherche soumise ici (par exemple, affichez un Toast)
        Toast.makeText(this, "Recherche soumise : " + query, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        setTitle(getString(R.string.list_of_activities));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.action_bar));

        if (savedInstanceState == null) {
            RechercheFragment rechercheFragment = new RechercheFragment();
            rechercheFragment.setOnSearchSubmitListener(this);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.searchLayout, rechercheFragment);
            fragmentTransaction.commit();


            Controleur controleur = Controleur.getInstance();
            List<ActiviteTouristique> data = controleur.getAllActiviteTouristique();
            for (int i = 0; i < data.size(); i++) {
                addActiviteTouristiqueFragment(new ActiviteTouristiqueFragment(data.get(i)));
            }
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("reloaded", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem switchMenuItem = menu.findItem(R.id.theme_switch);
        View actionView = switchMenuItem.getActionView();
        SwitchCompat switchCompat = actionView.findViewById(R.id.switch_button);
        String themeModeName = ThemePreference.getThemeMode(this);
        if (themeModeName.equals("DARK")) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ThemePreference.saveThemeMode(ListeActivity.this, ThemePreference.ThemeMode.DARK);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    ThemePreference.saveThemeMode(ListeActivity.this, ThemePreference.ThemeMode.LIGHT);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.français:
                this.changeLanguage("fr");
                return true;
            case R.id.anglais:
                this.changeLanguage("en");
                return true;
            case R.id.logout:
                Intent intent = new Intent(ListeActivity.this, LoginInscriptionActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addActiviteTouristiqueFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerLayout, fragment);
        fragmentTransaction.commit();
    }

    private void changeLanguage(String languageCode) {
        LanguagePreference.saveLanguage(this, languageCode);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(new Locale(languageCode));
        resources.updateConfiguration(configuration, displayMetrics);
        recreate();
    }
}