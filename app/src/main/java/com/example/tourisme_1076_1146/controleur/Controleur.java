package com.example.tourisme_1076_1146.controleur;

import com.example.tourisme_1076_1146.modele.ActiviteTouristique;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class Controleur {

    private static Controleur instance = null;

    private Controleur() {
        super();
    }

    public interface CallbackWebServiceGetAllActiviteTouristique {
        void onSuccess(List<ActiviteTouristique> liste);
        void onFailure(String error);
    }

    public static void getAllActiviteTouristique(CallbackWebServiceGetAllActiviteTouristique callback) throws Exception {
        try {
            Request request = new Request.Builder()
                    .url("https://android-back-m1.vercel.app/activities/")
                    .get()
                    .header("x-auth-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0Y2U0MjRiNTYyM2IyMGM3MmU5YmRhYSIsImlhdCI6MTY5MTMyMDk3NywiZXhwIjoxNjkxNDA3Mzc3fQ.kkCt1DnRWaWXgSw5oZnPrVfcW3Pv8PP0BLWoBkSmprY")
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    List<ActiviteTouristique> liste = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("_id");
                            String titre = jsonObject.getString("titre");
                            String description = jsonObject.getString("description");
                            double nbEtoiles = jsonObject.getDouble("averageEtoiles");
                            int nbVotes = jsonObject.getInt("voteCount");
                            String videoURL = jsonObject.getString("video");
                            JSONArray imagesArray = jsonObject.getJSONArray("images");
                            List<String> imagesURL = new ArrayList<>();
                            for (int j = 0; j < imagesArray.length(); j++)
                                imagesURL.add(imagesArray.getString(j));
                            ActiviteTouristique activiteTouristique = new ActiviteTouristique(id, titre, description, nbEtoiles, nbVotes, videoURL, imagesURL);
                            liste.add(activiteTouristique);
                        }
                        callback.onSuccess(liste);
                    } catch (JSONException e) {
                        callback.onFailure(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public interface CallbackWebServiceLoginInscription {
        void onSuccess(String token);
        void onFailure(String error);
    }

    public static void inscription(String nom, String prenom, String email, String mdp1, String mdp2, CallbackWebServiceLoginInscription callback) {
        try {
            RequestBody body = new FormBody.Builder()
                    .add("nom", nom)
                    .add("prenom", prenom)
                    .add("email", email)
                    .add("password", mdp1)
                    .add("passwordConf", mdp2)
                    .build();

            Request request = new Request.Builder()
                    .url("https://android-back-m1.vercel.app/users/register")
                    .post(body)
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    JSONObject jsonResponse = null;
                    try {
                        jsonResponse = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        callback.onFailure(e.getMessage());
                    }
                    try {
                        callback.onSuccess(jsonResponse.getString("token"));
                    } catch (JSONException e) {
                        try {
                            callback.onFailure(jsonResponse.getString("message"));
                        } catch (JSONException ex) {
                            callback.onFailure(ex.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static void authentification(String email, String mdp, CallbackWebServiceLoginInscription callback) throws Exception {
        try {
            RequestBody body = new FormBody.Builder()
                    .add("email", email)
                    .add("password", mdp)
                    .build();

            Request request = new Request.Builder()
                    .url("https://android-back-m1.vercel.app/users/login")
                    .post(body)
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    JSONObject jsonResponse = null;
                    try {
                        jsonResponse = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        callback.onFailure(e.getMessage());
                    }
                    try {
                        callback.onSuccess(jsonResponse.getString("token"));
                    } catch (JSONException e) {
                        try {
                            callback.onFailure(jsonResponse.getString("message"));
                        } catch (JSONException ex) {
                            callback.onFailure(ex.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static final Controleur getInstance() {
        if (Controleur.instance == null) {
            Controleur.instance = new Controleur();
        }
        return Controleur.instance;
    }
}
