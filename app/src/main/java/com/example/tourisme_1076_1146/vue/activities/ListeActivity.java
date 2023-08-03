package com.example.tourisme_1076_1146.vue.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.controleur.LanguagePreferences;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;
import com.example.tourisme_1076_1146.vue.fragments.ActiviteTouristiqueFragment;
import com.example.tourisme_1076_1146.vue.fragments.RechercheFragment;

import java.util.List;
import java.util.Locale;

public class ListeActivity extends AppCompatActivity implements RechercheFragment.OnSearchSubmitListener {

    DrawerLayout settings;

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
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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
//        FragmentContainerView container = new FragmentContainerView(this);
//        container.setId(ViewPager.generateViewId());
//        container.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        ));
//        getSupportFragmentManager().beginTransaction()
//                .replace(container.getId(), fragment)
//                .commit();
//
//        LinearLayout containerLayout = findViewById(R.id.containerLayout);
//        containerLayout.addView(container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerLayout, fragment);
        fragmentTransaction.commit();
    }

    private void changeLanguage(String languageCode) {
        LanguagePreferences.saveLanguage(this, languageCode);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(new Locale(languageCode));
        resources.updateConfiguration(configuration, displayMetrics);

        // Redémarrer l'activité actuelle pour appliquer les changements
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}