package com.example.tourisme_1076_1146.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActiviteTouristique implements Serializable {
    private String id;
    private String titre;
    private String description;
    private double nbEtoiles;
    private int nbVotes;
    private String videoURL;
    private List<String> imagesURL;

    public ActiviteTouristique(String id, String titre, String description, double nbEtoiles, int nbVotes, String videoURL, List<String> imagesURL) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.nbEtoiles = nbEtoiles;
        this.nbVotes = nbVotes;
        this.videoURL = videoURL;
        this.imagesURL = imagesURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(double nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public int getNbVotes() {
        return nbVotes;
    }

    public void setNbVotes(int nbVotes) {
        this.nbVotes = nbVotes;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public List<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }
}
