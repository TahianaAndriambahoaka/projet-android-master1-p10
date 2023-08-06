package com.example.tourisme_1076_1146.controleur;

import com.example.tourisme_1076_1146.modele.ActiviteTouristique;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    public List<ActiviteTouristique> getAllActiviteTouristique() {
        return ActiviteTouristique.getAll();
    }

    public interface CallbackWS {
        void onSuccess(String token);
        void onFailure(String error);
    }

    public static void inscription(String nom, String prenom, String email, String mdp1, String mdp2, CallbackWS callback) {
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

    public static void authentification(String email, String mdp, CallbackWS callback) throws Exception {
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
