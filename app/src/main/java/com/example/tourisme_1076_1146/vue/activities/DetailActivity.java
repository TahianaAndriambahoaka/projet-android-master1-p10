package com.example.tourisme_1076_1146.vue.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.controleur.LanguagePreference;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private ActiviteTouristique activiteTouristique;
    private Integer evaluation;
    private ImageView rating1;
    private ImageView rating2;
    private ImageView rating3;
    private ImageView rating4;
    private ImageView rating5;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String savedLanguage = LanguagePreference.getLanguage(this);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(new Locale(savedLanguage));
        resources.updateConfiguration(configuration, displayMetrics);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getString(R.string.details));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.action_bar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.init();

        this.ratingListener();

        if (this.evaluation > 0)  {
            if (this.evaluation >= 1) ((ImageView) findViewById(R.id.rating1)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
            if (this.evaluation >= 2) ((ImageView) findViewById(R.id.rating2)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
            if (this.evaluation >= 3) ((ImageView) findViewById(R.id.rating3)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
            if (this.evaluation >= 4) ((ImageView) findViewById(R.id.rating4)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
            if (this.evaluation == 5) ((ImageView)findViewById(R.id.rating5)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }

        String videoUrl = this.activiteTouristique.getVideoURL();
        WebView webView = findViewById(R.id.videoView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(videoUrl);

        ((TextView)findViewById(R.id.description)).setText(this.activiteTouristique.getDescription());
        ((TextView)findViewById(R.id.titre)).setText(this.activiteTouristique.getTitre());

        if (this.activiteTouristique.getNbEtoiles() >= 1) {
            if ((int)this.activiteTouristique.getNbEtoiles() == 1)
                ((ImageView) findViewById(R.id.etoile1)).setImageResource(R.drawable.ic_baseline_star_half_24);
            else
                ((ImageView) findViewById(R.id.etoile1)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
        if (this.activiteTouristique.getNbEtoiles() >= 2) {
            if ((int)this.activiteTouristique.getNbEtoiles() == 2)
                ((ImageView) findViewById(R.id.etoile2)).setImageResource(R.drawable.ic_baseline_star_half_24);
            else
                ((ImageView) findViewById(R.id.etoile2)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
        if (this.activiteTouristique.getNbEtoiles() >= 3) {
            if ((int)this.activiteTouristique.getNbEtoiles() == 3)
                ((ImageView) findViewById(R.id.etoile3)).setImageResource(R.drawable.ic_baseline_star_half_24);
            else
                ((ImageView) findViewById(R.id.etoile3)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
        if (this.activiteTouristique.getNbEtoiles() >= 4) {
            if ((int)this.activiteTouristique.getNbEtoiles() == 4)
                ((ImageView) findViewById(R.id.etoile4)).setImageResource(R.drawable.ic_baseline_star_half_24);
            else
                ((ImageView) findViewById(R.id.etoile4)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        }
        if ((int)this.activiteTouristique.getNbEtoiles() == 5) ((ImageView) findViewById(R.id.etoile5)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);

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

    private void ratingListener() {
        this.rating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.vote(1);
            }
        });
        this.rating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.vote(2);
            }
        });
        this.rating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.vote(3);
            }
        });
        this.rating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.vote(4);
            }
        });
        this.rating5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.vote(5);
            }
        });
    }

    private void vote(int vote) {
        Controleur.getInstance(this).mustReload = true;
        DetailActivity.this.swipeRefreshLayout.setRefreshing(true);
        try {
            Controleur.evaluation(this.activiteTouristique.getId(), vote, new Controleur.CallbackWebServiceEvaluation() {
                @Override
                public void onSuccess() {
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DetailActivity.this.evaluation = vote;
                            DetailActivity.this.swipeRefreshLayout.setRefreshing(false);
                            ((ImageView) findViewById(R.id.rating1)).setImageResource(R.drawable.ic_baseline_star_outline_24);
                            ((ImageView) findViewById(R.id.rating2)).setImageResource(R.drawable.ic_baseline_star_outline_24);
                            ((ImageView) findViewById(R.id.rating3)).setImageResource(R.drawable.ic_baseline_star_outline_24);
                            ((ImageView) findViewById(R.id.rating4)).setImageResource(R.drawable.ic_baseline_star_outline_24);
                            ((ImageView) findViewById(R.id.rating5)).setImageResource(R.drawable.ic_baseline_star_outline_24);
                            if (vote >= 1) ((ImageView) findViewById(R.id.rating1)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
                            if (vote >= 2) ((ImageView) findViewById(R.id.rating2)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
                            if (vote >= 3) ((ImageView) findViewById(R.id.rating3)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
                            if (vote >= 4) ((ImageView) findViewById(R.id.rating4)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
                            if (vote == 5) ((ImageView)findViewById(R.id.rating5)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
                        }
                    });
                }

                @Override
                public void onFailure(String err) {
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailActivity.this, err, Toast.LENGTH_SHORT).show();
                            DetailActivity.this.swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            });
        } catch (Exception e) {
            DetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    DetailActivity.this.swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("data") && intent.hasExtra("evaluation")) {
            this.activiteTouristique = (ActiviteTouristique) intent.getSerializableExtra("data");
            this.evaluation = (Integer) intent.getSerializableExtra("evaluation");
        }
        this.rating1 = findViewById(R.id.rating1);
        this.rating2 = findViewById(R.id.rating2);
        this.rating3 = findViewById(R.id.rating3);
        this.rating4 = findViewById(R.id.rating4);
        this.rating5 = findViewById(R.id.rating5);
        this.swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setEnabled(false);
    }
}