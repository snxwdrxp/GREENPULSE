package com.example.appli_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.appli_mobile.R;
import java.util.ArrayList;

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

        // simulation de données pour la liste des habitats
        habitats = new ArrayList<>();
        habitats.add("Habitat A - Consommation: 1200W");
        habitats.add("Habitat B - Consommation: 1500W");
        habitats.add("Habitat C - Consommation: 900W");
        habitats.add("Habitat D - Consommation: 2000W");
        habitats.add("Habitat E - Consommation: 1750W");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, habitats);
        listView.setAdapter(adapter);
        return view;
    }
}
