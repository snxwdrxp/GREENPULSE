package com.example.appli_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApplianceAdapter extends ArrayAdapter<Appliance> {
    private Context context;
    private List<Appliance> appliances;
    private String token; // Le token de l'utilisateur

    public ApplianceAdapter(Context context, List<Appliance> appliances, String token) {
        super(context, 0, appliances);
        this.context = context;
        this.appliances = appliances;
        this.token = token;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_appliance, parent, false);
        }

        Appliance appliance = appliances.get(position);
        TextView tvName = convertView.findViewById(R.id.tvApplianceName);
        TextView tvWattage = convertView.findViewById(R.id.tvApplianceWattage);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);

        tvName.setText(appliance.getName());
        tvWattage.setText("Puissance: " + appliance.getWattage() + "W");
        checkBox.setChecked(appliance.isChecked());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            appliance.setChecked(isChecked);
            if (isChecked) {
                // Envoi de la requête pour ajouter l'appareil à la base de données
                addApplianceToDatabase(appliance);
            }
        });

        return convertView;
    }

    private void addApplianceToDatabase(Appliance appliance) {
        // Envoie la requête HTTP pour ajouter l'appareil
        Ion.with(context)
                .load("GET", "http://10.0.2.2/powerhome/ajoutAppliance.php")
                .setBodyParameter("name", appliance.getName())
                .setBodyParameter("wattage", String.valueOf(appliance.getWattage()))
                .setBodyParameter("token", token)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            e.printStackTrace();
                            Toast.makeText(context, "Erreur d'ajout", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Traiter la réponse du serveur
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            String status = jsonResponse.getString("success");

                            if ("true".equals(status)) {
                                Toast.makeText(context, "Appareil ajouté avec succès. Réf: " + jsonResponse.getString("ref"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            Toast.makeText(context, "Erreur lors de la réponse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public List<Appliance> getSelectedAppliances() {
        List<Appliance> selected = new ArrayList<>();
        for (Appliance appliance : appliances) {
            if (appliance.isChecked()) {
                selected.add(appliance);
            }
        }
        return selected;
    }
}

