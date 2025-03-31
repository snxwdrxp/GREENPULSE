package com.example.appli_mobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.appli_mobile.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// fragment qui affiche la liste des habitats avec consommation simulée
public class HabitatListFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> habitats;

    public HabitatListFragment() {
        // rien à initialiser ici
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habitat_list, container, false);
        listView = view.findViewById(R.id.listHabitat);

        // Récupérer le token stocké dans SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");

        // 🚀 Vérification : voir si on récupère bien le token avant d'envoyer la requête
        Log.d("DEBUG_TOKEN_USAGE", "Token envoyé: " + token);

        // Faire une requête API pour récupérer les habitats
        Ion.with(getContext())
                .load("http://10.0.2.2/powerhome/getHabitats_v2.php?token=" + token)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // 🚀 Vérifier la réponse de l'API
                        Log.d("DEBUG_API_RESPONSE", "Réponse API: " + result);

                        try {
                            // Convertir la réponse en JSONArray
                            JSONArray jsonArray = new JSONArray(result);
                            HashMap<String, Integer> userWattages = new HashMap<>();

                            // Lire chaque habitat
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject habitat = jsonArray.getJSONObject(i);
                                JSONArray appliances = habitat.getJSONArray("appliances");
                                int totalWattage = 0;

                                // Lire les appareils et additionner les wattages
                                for (int j = 0; j < appliances.length(); j++) {
                                    JSONObject appliance = appliances.getJSONObject(j);
                                    totalWattage += appliance.getInt("wattage");
                                }

                                // Associer le wattage total à l'utilisateur (exemple avec floor)
                                String userName = "Habitat " + habitat.getString("id"); // Tu peux changer ça si tu as un vrai nom
                                if (userWattages.containsKey(userName)) {
                                    userWattages.put(userName, userWattages.get(userName) + totalWattage);
                                } else {
                                    userWattages.put(userName, totalWattage);
                                }
                            }

                            // Préparer la liste pour l'affichage
                            ArrayList<String> displayList = new ArrayList<>();
                            for (Map.Entry<String, Integer> entry : userWattages.entrySet()) {
                                displayList.add(entry.getKey() + " - Consommation: " + entry.getValue() + "W");
                            }

                            // Mettre à jour la ListView
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, displayList);
                            listView.setAdapter(adapter);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            Toast.makeText(getContext(), "Erreur de lecture des données", Toast.LENGTH_LONG).show();
                        }
                    }

                });

        return view;
    }

}
