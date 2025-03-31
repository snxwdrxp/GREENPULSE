package com.example.appli_mobile;

import android.content.Context;
import android.util.Log;
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
    private String token;

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

        // Fix du recyclage du CheckBox
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(appliance.isChecked());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            appliance.setChecked(isChecked);
            if (isChecked) {
                addApplianceToDatabase(appliance);
            }
        });

        return convertView;
    }

    private void addApplianceToDatabase(Appliance appliance) {
        String url = "http://10.0.2.2/powerhome/ajoutAppliance.php";

        Log.d("DEBUG_ION", "Envoi de la requête à: " + url);
        Log.d("DEBUG_ION", "Données envoyées: name=" + appliance.getName()
                + ", wattage=" + appliance.getWattage() + ", token=" + token);

        Ion.with(context)
                .load("POST", "http://10.0.2.2/powerhome/ajoutAppliance.php")
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

                        // Afficher la réponse brute
                        Log.d("DEBUG_JSON", "Réponse serveur brute: " + result);

                        try {
                            // Traiter la réponse en JSON
                            JSONObject jsonResponse = new JSONObject(result);
                            // Traiter le succès ou l'échec
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


