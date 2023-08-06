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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.controleur.LanguagePreference;
import com.example.tourisme_1076_1146.controleur.ThemePreference;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;
import com.example.tourisme_1076_1146.vue.fragments.ActiviteTouristiqueFragment;
import com.example.tourisme_1076_1146.vue.fragments.LoginFragment;
import com.example.tourisme_1076_1146.vue.fragments.RechercheFragment;

import java.util.List;
import java.util.Locale;

public class ListeActivity extends AppCompatActivity implements RechercheFragment.OnSearchSubmitListener {

    ImageView loading;

    @Override
    public void onSearchSubmit(String query) {
        // Traitez la recherche soumise ici (par exemple, affichez un Toast)
        Toast.makeText(this, "Recherche soumise : " + query, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String savedLanguage = LanguagePreference.getLanguage(this);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(new Locale(savedLanguage));
        resources.updateConfiguration(configuration, displayMetrics);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        setTitle(getString(R.string.list_of_activities));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.action_bar));

        this.loading = findViewById(R.id.loading);
        this.loading.setImageResource(R.drawable.loading_night);
        if (ThemePreference.getThemeMode(this).equals("DARK"))
            Glide.with(this).asGif().load(R.drawable.loading_night).into(this.loading);
        else
            Glide.with(this).asGif().load(R.drawable.loading_light).into(this.loading);

        if (savedInstanceState == null) {
            this.loading.setVisibility(View.VISIBLE);
            RechercheFragment rechercheFragment = new RechercheFragment();
            rechercheFragment.setOnSearchSubmitListener(this);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.searchLayout, rechercheFragment);
            fragmentTransaction.commit();


            try {
                Controleur.getAllActiviteTouristique(new Controleur.CallbackWebServiceGetAllActiviteTouristique() {
                    @Override
                    public void onSuccess(List<ActiviteTouristique> liste) {
                        ListeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < liste.size(); i++)
                                    addActiviteTouristiqueFragment(new ActiviteTouristiqueFragment(liste.get(i)));
                                ListeActivity.this.loading.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String err) {
                        ListeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ListeActivity.this, err, Toast.LENGTH_SHORT).show();
                                ListeActivity.this.loading.setVisibility(View.GONE);
                            }
                        });
                    }
                });
            } catch (Exception e) {
                ListeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        ListeActivity.this.loading.setVisibility(View.GONE);
                    }
                });
            }



        } else
            ListeActivity.this.loading.setVisibility(View.GONE);


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