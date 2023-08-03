package com.example.tourisme_1076_1146.controleur;

import com.example.tourisme_1076_1146.modele.ActiviteTouristique;
import com.example.tourisme_1076_1146.modele.Utilisateur;

import java.util.List;

public final class Controleur {

    private static Controleur instance = null;

    private Controleur() {
        super();
    }

    public List<ActiviteTouristique> getAllActiviteTouristique() {
        return ActiviteTouristique.getAll();
    }

    public Utilisateur inscription(String nom, String prenom, String email, String mdp) {
        return Utilisateur.inscription(new Utilisateur(nom, prenom, email, mdp));
    }

    public Utilisateur authentification(String email, String mdp) {
        return Utilisateur.authentification(new Utilisateur(null, null, email, mdp));
    }

    public static final Controleur getInstance() {
        if (Controleur.instance == null) {
            Controleur.instance = new Controleur();
        }
        return Controleur.instance;
    }
}
