package com.example.tourisme_1076_1146.vue.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.controleur.Controleur;
import com.example.tourisme_1076_1146.vue.activities.ListeActivity;
import com.google.android.material.textfield.TextInputLayout;

public class InscriptionFragment extends Fragment {
    private TextInputLayout nomInput;
    private TextInputLayout prenomInput;
    private TextInputLayout emailInput;
    private TextInputLayout mdp1Input;
    private TextInputLayout mdp2Input;
    private Button button;
    private Button error;
    private TextView link;
    private boolean isSubmitted;
    private String nom;
    private String prenom;
    private String email;
    private String mdp1;
    private String mdp2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inscription, container, false);

        this.init(v);

        this.nomInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InscriptionFragment.this.error.setVisibility(View.GONE);
                InscriptionFragment.this.nom = InscriptionFragment.this.nomInput.getEditText().getText().toString().trim();
                if (InscriptionFragment.this.isSubmitted)
                    InscriptionFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.prenomInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InscriptionFragment.this.error.setVisibility(View.GONE);
                InscriptionFragment.this.prenom = InscriptionFragment.this.prenomInput.getEditText().getText().toString().trim();
                if (InscriptionFragment.this.isSubmitted)
                    InscriptionFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.emailInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InscriptionFragment.this.error.setVisibility(View.GONE);
                InscriptionFragment.this.email = InscriptionFragment.this.emailInput.getEditText().getText().toString().trim();
                if (InscriptionFragment.this.isSubmitted)
                    InscriptionFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.mdp1Input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InscriptionFragment.this.error.setVisibility(View.GONE);
                InscriptionFragment.this.mdp1 = InscriptionFragment.this.mdp1Input.getEditText().getText().toString().trim();
                if (InscriptionFragment.this.isSubmitted)
                    InscriptionFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.mdp2Input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InscriptionFragment.this.error.setVisibility(View.GONE);
                InscriptionFragment.this.mdp2 = InscriptionFragment.this.mdp2Input.getEditText().getText().toString().trim();
                if (InscriptionFragment.this.isSubmitted)
                    InscriptionFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

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
                InscriptionFragment.this.isSubmitted = true;
                InscriptionFragment.this.button.setText(getString(R.string.loading));

                InscriptionFragment.this.controle();

                if (InscriptionFragment.this.isOK())
                    InscriptionFragment.this.inscription(nom, prenom, email, mdp1, mdp2);
                else
                    InscriptionFragment.this.button.setText(getString(R.string.sign_up));
            }
        });

        return v;
    }

    private boolean isOK() {
        return  InscriptionFragment.this.nomInput.getError()==null && InscriptionFragment.this.prenomInput.getError()==null && InscriptionFragment.this.emailInput.getError()==null && InscriptionFragment.this.mdp1Input.getError()==null && InscriptionFragment.this.mdp2Input.getError()==null;
    }

    private void inscription(String nom, String prenom, String email, String mdp1, String mdp2) {

        try {
            Controleur.getInstance().inscription(nom, prenom, email, mdp1, mdp2, new Controleur.CallbackWS() {
                @Override
                public void onSuccess(String token) {
                    InscriptionFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InscriptionFragment.this.button.setText(getString(R.string.sign_up));
                            Intent intent = new Intent(InscriptionFragment.this.getActivity(), ListeActivity.class);
                            InscriptionFragment.this.startActivity(intent);
                            InscriptionFragment.this.getActivity().finish();
                        }
                    });
                }

                @Override
                public void onFailure(String err) {
                    InscriptionFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (err.equals("Cette adresse e-mail est déjà utilisée")) {
                                InscriptionFragment.this.emailInput.setError(getString(R.string.email_already_used));
                                InscriptionFragment.this.error.setText(getString(R.string.email_already_used));
                            } else if (err.equals("Les mots de passe ne correspondent pas")) {
                                InscriptionFragment.this.mdp1Input.setError(getString(R.string.passwords_do_not_match));
                                InscriptionFragment.this.mdp2Input.setError(getString(R.string.passwords_do_not_match));
                                InscriptionFragment.this.error.setText(R.string.passwords_do_not_match);
                            } else {
                                InscriptionFragment.this.nomInput.setError(getString(R.string.sign_up_error));
                                InscriptionFragment.this.prenomInput.setError(getString(R.string.sign_up_error));
                                InscriptionFragment.this.emailInput.setError(getString(R.string.sign_up_error));
                                InscriptionFragment.this.mdp1Input.setError(getString(R.string.sign_up_error));
                                InscriptionFragment.this.mdp2Input.setError(getString(R.string.sign_up_error));
                                InscriptionFragment.this.error.setText(R.string.sign_up_error);
                            }
                            InscriptionFragment.this.button.setText(getString(R.string.sign_up));
                            InscriptionFragment.this.error.setVisibility(View.VISIBLE);
                            InscriptionFragment.this.error.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
                        }
                    });
                }
            });
        } catch (Exception e) {
            InscriptionFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    InscriptionFragment.this.nomInput.setError(getString(R.string.sign_up_error));
                    InscriptionFragment.this.prenomInput.setError(getString(R.string.sign_up_error));
                    InscriptionFragment.this.emailInput.setError(getString(R.string.sign_up_error));
                    InscriptionFragment.this.mdp1Input.setError(getString(R.string.sign_up_error));
                    InscriptionFragment.this.mdp2Input.setError(getString(R.string.sign_up_error));
                    InscriptionFragment.this.button.setText(getString(R.string.sign_up));
                    InscriptionFragment.this.error.setText(R.string.sign_up_error);
                    InscriptionFragment.this.error.setVisibility(View.VISIBLE);
                    InscriptionFragment.this.error.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
                }
            });
        }
    }

    private void controle() {
        if (this.nom.isEmpty())
            this.nomInput.setError(getString(R.string.required_field));
        else
            this.nomInput.setError(null);

        if (this.prenom.isEmpty())
            this.prenomInput.setError(getString(R.string.required_field));
        else
            this.prenomInput.setError(null);

        if (this.email.isEmpty())
            this.emailInput.setError(getString(R.string.required_field));
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this.email).matches())
            this.emailInput.setError(getString(R.string.invalid_email_address));
        else
            this.emailInput.setError(null);

        if (this.mdp1.isEmpty())
            this.mdp1Input.setError(getString(R.string.required_field));
        else
            this.mdp1Input.setError(null);

        if (this.mdp2.isEmpty())
            this.mdp2Input.setError(getString(R.string.required_field));
        else
            this.mdp2Input.setError(null);

        if (!this.mdp1.isEmpty() && !this.mdp2.isEmpty()) {
            if (!this.mdp1.equals(this.mdp2)) {
                this.mdp1Input.setError(getString(R.string.different_passwords));
                this.mdp2Input.setError(getString(R.string.different_passwords));
            } else {
                this.mdp1Input.setError(null);
                this.mdp2Input.setError(null);
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
        this.error = v.findViewById(R.id.error);
        this.link = v.findViewById(R.id.link);
        this.error.setVisibility(View.GONE);
        this.isSubmitted = false;
        this.nom = "";
        this.prenom = "";
        this.email = "";
        this.mdp1 = "";
        this.mdp2 = "";
    }
}