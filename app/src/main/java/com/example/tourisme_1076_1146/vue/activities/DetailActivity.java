package com.example.tourisme_1076_1146.vue.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.LanguagePreferences;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    ActiviteTouristique activiteTouristique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("data")) {
            this.activiteTouristique = (ActiviteTouristique) intent.getSerializableExtra("data");
        }

        setTitle(getString(R.string.details));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
        // Activer le bouton de retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String videoUrl = this.activiteTouristique.getVideoURL();
        WebView webView = findViewById(R.id.videoView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(videoUrl);

        TextView descriptionTitre = findViewById(R.id.descriptionTitre);
        String textDescriptionTitre = descriptionTitre.getText().toString();
        SpannableString spannableStringDescriptionTitre = new SpannableString(textDescriptionTitre);
        spannableStringDescriptionTitre.setSpan(new UnderlineSpan(), 0, textDescriptionTitre.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        descriptionTitre.setText(spannableStringDescriptionTitre);

        TextView galerieTitre = findViewById(R.id.galerieTitre);
        String textGalerieTitre = galerieTitre.getText().toString();
        SpannableString spannableStringGalerieTitre = new SpannableString(textGalerieTitre);
        spannableStringGalerieTitre.setSpan(new UnderlineSpan(), 0, textGalerieTitre.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        galerieTitre.setText(spannableStringGalerieTitre);

        ((TextView)findViewById(R.id.description)).setText(this.activiteTouristique.getDescription());
        ((TextView)findViewById(R.id.titre)).setText(this.activiteTouristique.getTitre());

        if (this.activiteTouristique.getNbEtoiles() >= 1) ((ImageView)findViewById(R.id.etoile1)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() >= 2) ((ImageView)findViewById(R.id.etoile2)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() >= 3) ((ImageView)findViewById(R.id.etoile3)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() >= 4) ((ImageView)findViewById(R.id.etoile4)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() == 5) ((ImageView)findViewById(R.id.etoile5)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);

        if (this.activiteTouristique.getNbVotes()<=1) {
            ((TextView) findViewById(R.id.nbRates)).setText("(" + this.activiteTouristique.getNbVotes() + " " + getString(R.string.vote) + ")");
        } else {
            ((TextView) findViewById(R.id.nbRates)).setText("(" + this.activiteTouristique.getNbVotes() + " " + getString(R.string.votes) + ")");
        }

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        List<SlideModel> slideModels = new ArrayList<>();
        for (int i = 0; i < this.activiteTouristique.getImagesURL().size(); i++)
            slideModels.add(new SlideModel(this.activiteTouristique.getImagesURL().get(i), ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}