package com.example.appli_mobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appli_mobile.Appliance;
import com.example.appli_mobile.ApplianceAdapter;
import com.example.appli_mobile.R;
import com.example.appli_mobile.utils.SessionManager;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// fragment pour afficher les détails de "mon habitat" avec consommation et éco-coins
public class MyHabitatFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvConsumption, tvBonusMalus, tvEcoCoins;

    private ApplianceAdapter adapter;
    private List<Appliance> applianceList;

    // constructeur vide
    public MyHabitatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_habitat, container, false);

        progressBar = view.findViewById(R.id.progressConsumption);
        tvConsumption = view.findViewById(R.id.tvConsumption);
        tvBonusMalus = view.findViewById(R.id.tvBonusMalus);
        tvEcoCoins = view.findViewById(R.id.tvEcoCoins);
        ListView listView = view.findViewById(R.id.listview1);

        applianceList = new ArrayList<>();

        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");

        // Liste des appareils courants chez les Kabyles avec leur puissance
        applianceList.add(new Appliance("Réfrigérateur", 800));
        applianceList.add(new Appliance("Télévision (LED)", 150));
        applianceList.add(new Appliance("Machine à laver", 1200));
        applianceList.add(new Appliance("Cuisinière électrique", 2000));
        applianceList.add(new Appliance("Chauffage électrique", 1500));
        applianceList.add(new Appliance("Four à micro-ondes", 1000));
        applianceList.add(new Appliance("Fer à repasser", 1200));
        applianceList.add(new Appliance("Mixeur", 300));
        applianceList.add(new Appliance("Chargeur de téléphone", 15));
        applianceList.add(new Appliance("Lave-vaisselle", 1800));

        adapter = new ApplianceAdapter(requireContext(),applianceList , token);
        listView.setAdapter(adapter);

        // simulation de consommation en pourcentage
        Ion.with(getContext())
                .load("http://10.0.2.2/powerhome/consomation.php?token=" + token)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Affiche la réponse brute du serveur
                        Log.d("DEBUG_JSON", "Réponse du serveur : " + result);

                        try {
                            // Si la réponse est une erreur, elle sera retournée en format JSON
                            JSONObject jsonResponse = new JSONObject(result);

                            // Vérification si une erreur est présente dans la réponse
                            if (jsonResponse.has("error")) {
                                String errorMessage = jsonResponse.getString("error");
                                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                                return;
                            }

                            // Récupérer la consommation totale (en watt)
                            int totalWattage = jsonResponse.getInt("totalWattage");

                            // Calcul du pourcentage de consommation
                            int consumptionPercent = (totalWattage * 100) / 2000; // 2000W est la consommation maximale (ajuste selon tes besoins)

                            // Limite la consommation à 100% au maximum
                            if (consumptionPercent > 100) {
                                consumptionPercent = 100;
                            }

                            // Mettre à jour l'affichage
                            tvConsumption.setText("Consommation Totale: " + totalWattage + "W");
                            progressBar.setProgress(consumptionPercent);  // Mise à jour de la barre de progression

                            // Simulation du système de bonus/malus
                            int deltaEcoCoin = 0;
                            if (consumptionPercent < 30) {
                                tvBonusMalus.setText("Bonus: +5 éco-coins");
                                deltaEcoCoin = 5;
                            } else if (consumptionPercent > 70) {
                                tvBonusMalus.setText("Malus: -5 éco-coins");
                                deltaEcoCoin = -5;
                            } else {
                                tvBonusMalus.setText("Aucun bonus/malus");
                            }

                            // Mise à jour du solde d'éco-coins
                            SessionManager session = new SessionManager(getActivity());
                            // Ajout du deltaEcoCoin au solde existant
                            session.updateEcoCoins(deltaEcoCoin);
                            int solde = session.getEcoCoins();
                            tvEcoCoins.setText("Solde éco-coins: " + solde);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            Toast.makeText(getContext(), "Erreur de lecture des données", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        return view;
    }
}
