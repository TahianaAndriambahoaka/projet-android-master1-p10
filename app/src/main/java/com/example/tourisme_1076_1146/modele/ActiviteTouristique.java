package com.example.tourisme_1076_1146.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActiviteTouristique {
    private String id;
    private String titre;
    private String description;
    private int nbEtoiles;
    private int nbVotes;
    private String videoURL;
    private List<String> imagesURL;

    public ActiviteTouristique(String id, String titre, String description, int nbEtoiles, int nbVotes, String videoURL, List<String> imagesURL) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.nbEtoiles = nbEtoiles;
        this.nbVotes = nbVotes;
        this.videoURL = videoURL;
        this.imagesURL = imagesURL;
    }

    public static List<ActiviteTouristique> getAll() {
        List<ActiviteTouristique> data = new ArrayList<>();

        String loremImpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String video = "https://www.youtube.com/embed/Nya6D8mKtSo";
        List<String> images = new ArrayList<>(Arrays.asList("https://picsum.photos/id/237/200/300", "https://picsum.photos/seed/picsum/200/300", "https://picsum.photos/200/300?grayscale"));

        data.add(new ActiviteTouristique("1", "Activité touristique numero 1", loremImpsum, 5, 10, video, images));
        data.add(new ActiviteTouristique("2", "Activité 2", loremImpsum, 4, 10, video, images));
        data.add(new ActiviteTouristique("3", "Activité 3", loremImpsum, 3, 10, video, images));
        data.add(new ActiviteTouristique("4", "Activité 4", loremImpsum, 2, 10, video, images));
        data.add(new ActiviteTouristique("5", "Activité 5", loremImpsum, 1, 10, video, images));
        data.add(new ActiviteTouristique("6", "Activité 6", loremImpsum, 5, 10, video, images));
        data.add(new ActiviteTouristique("7", "Activité 7", loremImpsum, 4, 10, video, images));
        data.add(new ActiviteTouristique("8", "Activité 8", loremImpsum, 3, 10, video, images));
        data.add(new ActiviteTouristique("9", "Activité 9", loremImpsum, 2, 10, video, images));
        data.add(new ActiviteTouristique("10", "Activité 10", loremImpsum, 1, 10, video, images));

        return data;
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

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
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
