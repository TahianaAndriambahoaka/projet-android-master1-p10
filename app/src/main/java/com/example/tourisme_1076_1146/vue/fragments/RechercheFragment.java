package com.example.tourisme_1076_1146.vue.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tourisme_1076_1146.R;


public class RechercheFragment extends Fragment {

    public interface OnSearchSubmitListener {
        void onSearchSubmit(String query);
    }

    private OnSearchSubmitListener searchSubmitListener;
    private SearchView searchView;

    public void setOnSearchSubmitListener(OnSearchSubmitListener listener) {
        searchSubmitListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recherche, container, false);

        this.searchView = v.findViewById(R.id.searchView);
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchSubmitListener != null) {
                    searchSubmitListener.onSearchSubmit(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchSubmitListener != null) {
                    searchSubmitListener.onSearchSubmit(newText);
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchSubmitListener != null) {
                    searchSubmitListener.onSearchSubmit(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchSubmitListener != null) {
                    searchSubmitListener.onSearchSubmit(newText);
                }
                return true;
            }
        });
    }
}