package com.example.tourisme_1076_1146.vue.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.modele.ActiviteTouristique;
import com.example.tourisme_1076_1146.vue.activities.DetailActivity;

public class ActiviteTouristiqueFragment extends Fragment {
    private ActiviteTouristique activiteTouristique;

    public ActiviteTouristiqueFragment() {
        super();
    }

    public ActiviteTouristiqueFragment(ActiviteTouristique activiteTouristique) {
        this.activiteTouristique = activiteTouristique;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.activiteTouristique = (ActiviteTouristique) savedInstanceState.getSerializable("activite_touristique");
        }

        View v = inflater.inflate(R.layout.fragment_activite_touristique, container, false);

        ((TextView)v.findViewById(R.id.Titre)).setText(this.activiteTouristique.getTitre());

        if (this.activiteTouristique.getNbVotes()<=1) {
            ((TextView) v.findViewById(R.id.nbRates)).setText("(" + this.activiteTouristique.getNbVotes() + " " + getString(R.string.vote) + ")");
        } else {
            ((TextView) v.findViewById(R.id.nbRates)).setText("(" + this.activiteTouristique.getNbVotes() + " " + getString(R.string.votes) + ")");
        }

        if (this.activiteTouristique.getNbEtoiles() >= 1) ((ImageView)v.findViewById(R.id.etoile1)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() >= 2) ((ImageView)v.findViewById(R.id.etoile2)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() >= 3) ((ImageView)v.findViewById(R.id.etoile3)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() >= 4) ((ImageView)v.findViewById(R.id.etoile4)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);
        if (this.activiteTouristique.getNbEtoiles() == 5) ((ImageView)v.findViewById(R.id.etoile5)).setImageResource(R.drawable.ic_baseline_star_rate_yellow_24);

        ImageView imageView = v.findViewById(R.id.image);
        Glide.with(this)
                .load(this.activiteTouristique.getImagesURL().get(0))
                .into(imageView);

        v.findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("data", ActiviteTouristiqueFragment.this.activiteTouristique);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("activite_touristique", this.activiteTouristique);
    }
}