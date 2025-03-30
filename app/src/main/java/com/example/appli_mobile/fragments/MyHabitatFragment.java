package com.example.appli_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.appli_mobile.R;
import com.example.appli_mobile.utils.SessionManager;

// fragment pour afficher les détails de "mon habitat" avec consommation et éco-coins
public class MyHabitatFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvConsumption, tvBonusMalus, tvEcoCoins;

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

        // simulation de consommation en pourcentage
        int consumptionPercent = 65; // par exemple
        progressBar.setProgress(consumptionPercent);
        tvConsumption.setText("Consommation: " + consumptionPercent + "%");

        // simulation d'un système bonus/malus
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

        // On simule la mise à jour du solde d'éco-coins (ici, on ne fait qu'une simulation statique)
        // dans une vraie appli, on récupérerait le solde actuel depuis le SessionManager ou via une API
        SessionManager session = new SessionManager(getActivity());
        // ici, on simule la mise à jour en ajoutant deltaEcoCoin au solde existant
        session.updateEcoCoins(deltaEcoCoin);
        int solde = session.getEcoCoins();
        tvEcoCoins.setText("Solde éco-coins: " + solde);

        return view;
    }
}
