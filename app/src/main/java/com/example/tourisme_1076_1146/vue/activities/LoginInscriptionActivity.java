package com.example.tourisme_1076_1146.vue.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tourisme_1076_1146.R;
import com.example.tourisme_1076_1146.vue.fragments.InscriptionFragment;
import com.example.tourisme_1076_1146.vue.fragments.LoginFragment;

public class LoginInscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inscription);

        LoginFragment loginFragment = new LoginFragment();
        InscriptionFragment inscriptionFragment = new InscriptionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.card, loginFragment);
        fragmentTransaction.addToBackStack(null); // Pour permettre le retour en arrière avec le bouton de retour
        fragmentTransaction.commit();
    }
}