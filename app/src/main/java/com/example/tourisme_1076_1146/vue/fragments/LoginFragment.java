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

public class LoginFragment extends Fragment {
    private TextInputLayout emailInput;
    private TextInputLayout mdpInput;
    private Button button;
    private Button error;
    private TextView link;
    private boolean isSubmitted;
    private String email;
    private String mdp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        this.init(v);

        this.emailInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LoginFragment.this.error.setVisibility(View.GONE);
                LoginFragment.this.email = LoginFragment.this.emailInput.getEditText().getText().toString().trim();
                if (LoginFragment.this.isSubmitted)
                    LoginFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.mdpInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LoginFragment.this.error.setVisibility(View.GONE);
                LoginFragment.this.mdp = LoginFragment.this.mdpInput.getEditText().getText().toString().trim();
                if (LoginFragment.this.isSubmitted)
                    LoginFragment.this.controle();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.card, new InscriptionFragment())
                        .commit();
            }
        });

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.this.isSubmitted = true;
                LoginFragment.this.button.setText(getString(R.string.loading));

                LoginFragment.this.controle();

                if (LoginFragment.this.isOK())
                    LoginFragment.this.authentification();
                else
                    LoginFragment.this.button.setText(getString(R.string.connect));
            }
        });

        return v;
    }

    private boolean isOK() {
        return LoginFragment.this.emailInput.getError()==null && LoginFragment.this.mdpInput.getError()==null;
    }

    private void authentification() {
        try {
            Controleur.getInstance().authentification(this.email, this.mdp, new Controleur.CallbackWebServiceLoginInscription() {
                @Override
                public void onSuccess(String token) {
                    LoginFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LoginFragment.this.button.setText(getString(R.string.connect));
                            Intent intent = new Intent(LoginFragment.this.getActivity(), ListeActivity.class);
                            LoginFragment.this.startActivity(intent);
                            LoginFragment.this.getActivity().finish();
                        }
                    });
                }

                @Override
                public void onFailure(String err) {
                    LoginFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (err.equals("Mot de passe incorrect")) {
                                LoginFragment.this.error.setText(getString(R.string.incorrect_password));
                                LoginFragment.this.mdpInput.setError(getString(R.string.incorrect_password));
                            }
                            else {
                                LoginFragment.this.error.setText(getString(R.string.user_not_found));
                                LoginFragment.this.emailInput.setError(getString(R.string.user_not_found));
                                LoginFragment.this.mdpInput.setError(getString(R.string.user_not_found));
                            }
                            LoginFragment.this.button.setText(getString(R.string.connect));
                            LoginFragment.this.error.setVisibility(View.VISIBLE);
                            LoginFragment.this.error.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
                        }
                    });
                }
            });
        } catch (Exception e) {
            LoginFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoginFragment.this.button.setText(getString(R.string.connect));
                    LoginFragment.this.error.setText(R.string.authentication_error);
                    LoginFragment.this.error.setVisibility(View.VISIBLE);
                    LoginFragment.this.error.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pink));
                    LoginFragment.this.emailInput.setError(getString(R.string.authentication_error));
                    LoginFragment.this.mdpInput.setError(getString(R.string.authentication_error));
                }
            });
        }
    }


    private void controle() {
        if (this.email.isEmpty())
            this.emailInput.setError(getString(R.string.required_field));
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this.email).matches())
            this.emailInput.setError(getString(R.string.invalid_email_address));
        else
            this.emailInput.setError(null);

        if (this.mdp.isEmpty())
            this.mdpInput.setError(getString(R.string.required_field));
        else
            this.mdpInput.setError(null);
    }

    private void init(View v) {
        this.emailInput = v.findViewById(R.id.emailInput);
        this.mdpInput = v.findViewById(R.id.mdpInput);
        this.link = v.findViewById(R.id.link);
        this.button = v.findViewById(R.id.button);
        this.error = v.findViewById(R.id.error);
        this.error.setVisibility(View.GONE);
        this.isSubmitted = false;
        this.email = "";
        this.mdp = "";
    }

}