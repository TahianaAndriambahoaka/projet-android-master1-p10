package com.example.tourisme_1076_1146.vue.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.modele.Utilisateur;
import com.example.tourisme_1076_1146.vue.activities.ListeActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {
    private TextInputLayout emailInput;
    private TextInputLayout mdpInput;
    private Button button;
    private TextView link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        this.init(v);

        this.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InscriptionFragment inscriptionFragment = new InscriptionFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.card, inscriptionFragment)
                        .commit();
            }
        });

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.this.button.setText(getString(R.string.loading));

                String email = LoginFragment.this.emailInput.getEditText().getText().toString().trim();
                String mdp = LoginFragment.this.mdpInput.getEditText().getText().toString().trim();

                LoginFragment.this.controle(email, mdp);

                if (LoginFragment.this.isOK())
                    LoginFragment.this.authentification(email, mdp);

                LoginFragment.this.button.setText(getString(R.string.connect));

                if (LoginFragment.this.isOK()) {
                    Intent intent = new Intent(getActivity(), ListeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return v;
    }

    private boolean isOK() {
        return LoginFragment.this.emailInput.getError()==null && LoginFragment.this.mdpInput.getError()==null;
    }

    private void authentification(String email, String mdp) {
        Controleur controleur = Controleur.getInstance();
        Utilisateur utilisateur = controleur.authentification(email, mdp);
        if (utilisateur==null) {
            LoginFragment.this.emailInput.setError(getString(R.string.authentication_error));
            LoginFragment.this.mdpInput.setError(getString(R.string.authentication_error));
        }
    }

    private void controle(String email, String mdp) {
        if (email.isEmpty()) {
            LoginFragment.this.emailInput.setError(getString(R.string.required_field));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            LoginFragment.this.emailInput.setError(getString(R.string.invalid_email_address));
        } else {
            LoginFragment.this.emailInput.setError(null);
        }

        if (mdp.isEmpty()) {
            LoginFragment.this.mdpInput.setError(getString(R.string.required_field));
        } else {
            LoginFragment.this.mdpInput.setError(null);
        }
    }

    private void init(View v) {
        this.emailInput = v.findViewById(R.id.emailInput);
        this.mdpInput = v.findViewById(R.id.mdpInput);
        this.link = v.findViewById(R.id.link);
        this.button = v.findViewById(R.id.button);
    }

}