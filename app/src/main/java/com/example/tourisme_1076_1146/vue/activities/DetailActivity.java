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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.LanguagePreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getString(R.string.details));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
        // Activer le bouton de retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String videoUrl = "https://www.youtube.com/embed/Nya6D8mKtSo";
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

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://picsum.photos/id/237/200/300", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale", ScaleTypes.CENTER_CROP));
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