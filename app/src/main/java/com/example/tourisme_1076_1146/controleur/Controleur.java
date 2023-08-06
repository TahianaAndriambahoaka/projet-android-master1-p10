package com.example.tourisme_1076_1146.controleur;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

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
    private static Context context;

    private Controleur(Context context) {
        super();
        this.context = context;
    }

    public interface CallbackWebServiceEvaluation {
        void onSuccess();
        void onFailure(String error);
    }

    public static void evaluation(String idActivite, int vote, CallbackWebServiceEvaluation callback) throws Exception {
        try {
            RequestBody body = new FormBody.Builder()
                    .add("vote", Integer.toString(vote))
                    .build();

            Request request = new Request.Builder()
                    .url("https://android-back-m1.vercel.app/activities/"+idActivite+"/vote")
                    .put(body)
                    .header("x-auth-token", PreferenceManager.getDefaultSharedPreferences(Controleur.context).getString("token", ""))
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    JSONObject jsonResponse = null;
                    try {
                        jsonResponse = new JSONObject(response.body().string());
                        String message = jsonResponse.getString("message");
                        if (message.equals("Vote updated successfully"))
                            callback.onSuccess();
                        else
                            callback.onFailure(message);
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

    public interface CallbackWebServiceGetAllActiviteTouristique {
        void onSuccess(List<ActiviteTouristique> liste, List<Integer> evaluations);
        void onFailure(String error);
    }

    public static void getAllActiviteTouristique(CallbackWebServiceGetAllActiviteTouristique callback) throws Exception {
        try {
            Request request = new Request.Builder()
                    .url("https://android-back-m1.vercel.app/activities/")
                    .get()
                    .header("x-auth-token", PreferenceManager.getDefaultSharedPreferences(Controleur.context).getString("token", ""))
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    List<ActiviteTouristique> liste = new ArrayList<>();
                    List<Integer> evaluations = new ArrayList<>();
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
                            JSONArray etoilesArray = jsonObject.getJSONArray("etoiles");
                            int etoileNumber = 0;
                            for (int j = 0; j < etoilesArray.length(); j++) {
                                JSONArray etoileElement = etoilesArray.getJSONArray(j);
                                String userID = etoileElement.getString(0);
                                if (userID.equals(PreferenceManager.getDefaultSharedPreferences(Controleur.context).getString("id", ""))) {
                                    JSONObject etoileValue = etoileElement.getJSONObject(1);
                                    etoileNumber = etoileValue.getInt("$numberInt");
                                    evaluations.add(etoileNumber);
                                    break;
                                }
                            }
                            evaluations.add(etoileNumber);
                        }
                        callback.onSuccess(liste, evaluations);
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
        void onSuccess();
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
                        String id = jsonResponse.getJSONObject("user").getString("_id");
                        String token = jsonResponse.getString("token");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Controleur.context);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", id);
                        editor.putString("token", token);
                        editor.apply();
                        callback.onSuccess();
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
                        String id = jsonResponse.getJSONObject("user").getString("_id");
                        String token = jsonResponse.getString("token");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Controleur.context);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", id);
                        editor.putString("token", token);
                        editor.apply();
                        callback.onSuccess();
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

    public static final Controleur getInstance(Context context) {
        if (Controleur.instance == null) {
            Controleur.instance = new Controleur(context);
        }
        Controleur.context = context;
        return Controleur.instance;
    }
}
