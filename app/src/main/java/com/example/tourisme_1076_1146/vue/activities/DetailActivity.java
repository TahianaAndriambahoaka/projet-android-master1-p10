package com.example.tourisme_1076_1146.vue.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;

import java.util.ArrayList;
import java.util.List;

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
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.action_bar));
        // Activer le bouton de retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String videoUrl = this.activiteTouristique.getVideoURL();
//        videoUrl = "https://l.facebook.com/l.php?u=https%3A%2F%2Fcdn.videvo.net%2Fvidevo_files%2Fvideo%2Fpremium%2Fvideo0394%2Flarge_watermarked%2F902-1_902-0408-PD2_preview.mp4%3Ffbclid%3DIwAR3erlN5rhPhxvBny-SQ7H9n6-W0H5Sfj0-eVECI74ImSyBlSFL40vcKW_I&h=AT0XFVyT19SF6DC0biLpKOdpo8EHN-1OProVoW2IViR6WXZcLqX0cGi3GlsFQqgWbbVTOh1jLOsMPfkcftSQSev5g7QF44OO9sKaxKeqIu1f7LP-KhX3GQUD7xD8KCohRQtxKw";
        WebView webView = findViewById(R.id.videoView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(videoUrl);

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