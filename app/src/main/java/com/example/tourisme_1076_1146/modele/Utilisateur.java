package com.example.tourisme_1076_1146.modele;

public class Utilisateur {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;

    public Utilisateur(String nom, String prenom, String email, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
    }

    public static Utilisateur inscription(Utilisateur utilisateur) {
        return utilisateur;
    }

    public static Utilisateur authentification(Utilisateur utilisateur) {
        Utilisateur u = null;

        Utilisateur temp = new Utilisateur("coco", "rico", "coco@rico.co", "cocorico");
        if (utilisateur.email.equals(temp.email) && utilisateur.mdp.equals(temp.mdp)) {
            u = temp;
        }

        return u;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
