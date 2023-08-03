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

public class InscriptionFragment extends Fragment {
    private TextInputLayout nomInput;
    private TextInputLayout prenomInput;
    private TextInputLayout emailInput;
    private TextInputLayout mdp1Input;
    private TextInputLayout mdp2Input;
    private Button button;
    private TextView link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inscription, container, false);

        this.init(v);

        this.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.card, loginFragment)
                        .commit();
            }
        });

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InscriptionFragment.this.button.setText(getString(R.string.loading));

                String nom = InscriptionFragment.this.nomInput.getEditText().getText().toString().trim();
                String prenom = InscriptionFragment.this.prenomInput.getEditText().getText().toString().trim();
                String email = InscriptionFragment.this.emailInput.getEditText().getText().toString().trim();
                String mdp1 = InscriptionFragment.this.mdp1Input.getEditText().getText().toString().trim();
                String mdp2 = InscriptionFragment.this.mdp2Input.getEditText().getText().toString().trim();

                InscriptionFragment.this.controle(nom, prenom, email, mdp1, mdp2);

                if (InscriptionFragment.this.isOK())
                    InscriptionFragment.this.inscription(nom, prenom, email, mdp1);

                InscriptionFragment.this.button.setText(getString(R.string.sign_up));

                if (InscriptionFragment.this.isOK()) {
                    Intent intent = new Intent(getActivity(), ListeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return v;
    }

    private boolean isOK() {
        return  InscriptionFragment.this.nomInput.getError()==null && InscriptionFragment.this.prenomInput.getError()==null && InscriptionFragment.this.emailInput.getError()==null && InscriptionFragment.this.mdp1Input.getError()==null && InscriptionFragment.this.mdp2Input.getError()==null;
    }

    private void inscription(String nom, String prenom, String email, String mdp) {
        Controleur controleur = Controleur.getInstance();
        Utilisateur utilisateur = controleur.inscription(nom, prenom, email, mdp);
        if (utilisateur==null) {
            InscriptionFragment.this.nomInput.setError(getString(R.string.sign_up_error));
            InscriptionFragment.this.prenomInput.setError(getString(R.string.sign_up_error));
            InscriptionFragment.this.emailInput.setError(getString(R.string.sign_up_error));
            InscriptionFragment.this.mdp1Input.setError(getString(R.string.sign_up_error));
            InscriptionFragment.this.mdp2Input.setError(getString(R.string.sign_up_error));
        }
    }

    private void controle(String nom, String prenom, String email, String mdp1, String mdp2) {
        if (nom.isEmpty()) {
            InscriptionFragment.this.nomInput.setError(getString(R.string.required_field));
        } else {
            InscriptionFragment.this.nomInput.setError(null);
        }

        if (prenom.isEmpty()) {
            InscriptionFragment.this.prenomInput.setError(getString(R.string.required_field));
        } else {
            InscriptionFragment.this.prenomInput.setError(null);
        }

        if (email.isEmpty()) {
            InscriptionFragment.this.emailInput.setError(getString(R.string.required_field));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            InscriptionFragment.this.emailInput.setError(getString(R.string.invalid_email_address));
        } else {
            InscriptionFragment.this.emailInput.setError(null);
        }

        if (mdp1.isEmpty()) {
            InscriptionFragment.this.mdp1Input.setError(getString(R.string.required_field));
        } else {
            InscriptionFragment.this.mdp1Input.setError(null);
        }

        if (mdp2.isEmpty()) {
            InscriptionFragment.this.mdp2Input.setError(getString(R.string.required_field));
        } else {
            InscriptionFragment.this.mdp2Input.setError(null);
        }

        if (!mdp1.isEmpty() && !mdp2.isEmpty()) {
            if (!mdp1.equals(mdp2)) {
                InscriptionFragment.this.mdp1Input.setError(getString(R.string.different_passwords));
                InscriptionFragment.this.mdp2Input.setError(getString(R.string.different_passwords));
            } else {
                InscriptionFragment.this.mdp1Input.setError(null);
                InscriptionFragment.this.mdp2Input.setError(null);
            }
        }
    }

    private void init(View v) {
        this.nomInput = v.findViewById(R.id.nomInput);
        this.prenomInput = v.findViewById(R.id.prenomInput);
        this.emailInput = v.findViewById(R.id.emailInput);
        this.mdp1Input = v.findViewById(R.id.mdp1Input);
        this.mdp2Input = v.findViewById(R.id.mdp2Input);
        this.button = v.findViewById(R.id.button);
        this.link = v.findViewById(R.id.link);
    }
}