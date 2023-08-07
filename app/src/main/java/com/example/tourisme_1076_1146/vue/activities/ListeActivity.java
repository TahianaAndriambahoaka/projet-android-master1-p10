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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.controleur.LanguagePreference;
import com.example.tourisme_1076_1146.controleur.ThemePreference;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;
import com.example.tourisme_1076_1146.vue.fragments.ActiviteTouristiqueFragment;
import com.example.tourisme_1076_1146.vue.fragments.RechercheFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListeActivity extends AppCompatActivity implements RechercheFragment.OnSearchSubmitListener {
;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout containerLayout;
    ImageView loading;
    List<ActiviteTouristique> listeActiviteTouristique;
    List<Integer> listeEvaluation;


    @Override
    public void onSearchSubmit(String query) {
        List<Integer> indices = new ArrayList<>();
        query = query.toLowerCase();

        for (int i = 0; i < this.containerLayout.getChildCount(); i++) {
            View fragmentToHide = this.containerLayout.getChildAt(i);
            fragmentToHide.setVisibility(View.GONE);
        }
        for (int i = 0; i < this.listeActiviteTouristique.size(); i++) {
            ActiviteTouristique at = this.listeActiviteTouristique.get(i);
            String titre = at.getTitre().toLowerCase();
            if (titre.contains(query))
                indices.add(i);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null && fragment.getView() != null && fragment.getView().getParent() == ListeActivity.this.containerLayout)
                fragmentManager.beginTransaction().remove(fragment).commit();
        }
        for (int i = 0; i < indices.size(); i++)
            addActiviteTouristiqueFragment(new ActiviteTouristiqueFragment(this.listeActiviteTouristique.get(indices.get(i)), this.listeEvaluation.get(indices.get(i))));
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

        this.containerLayout = findViewById(R.id.containerLayout);

        this.loading = findViewById(R.id.loading);
        if (ThemePreference.getThemeMode(this).equals("DARK"))
            Glide.with(this).asGif().load(R.drawable.loading_night).into(this.loading);
        else
            Glide.with(this).asGif().load(R.drawable.loading_light).into(this.loading);

        if (savedInstanceState == null) {
            this.loading.setVisibility(View.VISIBLE);

            ListeActivity.this.listeActiviteTouristique = new ArrayList<>();
            ListeActivity.this.listeEvaluation = new ArrayList<>();

            this.loadData(false);
        } else {
            this.listeActiviteTouristique = (List<ActiviteTouristique>) savedInstanceState.getSerializable("listeActiviteTouristique");
            this.listeEvaluation = (List<Integer>) savedInstanceState.getSerializable("listeEvaluation");
            ListeActivity.this.loading.setVisibility(View.GONE);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        RechercheFragment rechercheFragment = new RechercheFragment();
        rechercheFragment.setOnSearchSubmitListener(this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.searchLayout, rechercheFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();




        this.swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ListeActivity.this.loadData(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Controleur.getInstance(this).mustReload) {
            swipeRefreshLayout.setRefreshing(true);
            this.loadData(true);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listeActiviteTouristique", (Serializable) this.listeActiviteTouristique);
        outState.putSerializable("listeEvaluation", (Serializable) this.listeEvaluation);
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

    private void loadData(boolean reload) {
        try {
            Controleur.getAllActiviteTouristique(new Controleur.CallbackWebServiceGetAllActiviteTouristique() {
                @Override
                public void onSuccess(List<ActiviteTouristique> liste, List<Integer> evaluations) {
                    ListeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeActivity.this.listeActiviteTouristique = liste;
                            ListeActivity.this.listeEvaluation = evaluations;
                            if (reload) {
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                for (Fragment fragment : fragmentManager.getFragments()) {
                                    if (fragment != null && fragment.getView() != null && fragment.getView().getParent() == ListeActivity.this.containerLayout)
                                        fragmentManager.beginTransaction().remove(fragment).commit();
                                }
                            }
                            for (int i = 0; i < liste.size(); i++)
                                addActiviteTouristiqueFragment(new ActiviteTouristiqueFragment(liste.get(i), evaluations.get(i)));
                            ListeActivity.this.loading.setVisibility(View.GONE);
                            if (reload)
                                ListeActivity.this.swipeRefreshLayout.setRefreshing(false);
                            if (Controleur.getInstance(ListeActivity.this).mustReload)
                                Controleur.getInstance(ListeActivity.this).mustReload = false;
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
                            if (reload)
                                ListeActivity.this.swipeRefreshLayout.setRefreshing(false);
                            if (Controleur.getInstance(ListeActivity.this).mustReload)
                                Controleur.getInstance(ListeActivity.this).mustReload = false;
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
                    if (reload)
                        ListeActivity.this.swipeRefreshLayout.setRefreshing(false);
                    if (Controleur.getInstance(ListeActivity.this).mustReload)
                        Controleur.getInstance(ListeActivity.this).mustReload = false;
                }
            });
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