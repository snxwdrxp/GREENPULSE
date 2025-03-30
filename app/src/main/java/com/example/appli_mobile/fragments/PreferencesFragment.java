package com.example.appli_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.appli_mobile.R;

// fragment pour afficher des préférences simulées
public class PreferencesFragment extends Fragment {

    // constructeur vide
    public PreferencesFragment() {
        // rieeeeen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        TextView tv = view.findViewById(R.id.tvPreferences);
        tv.setText("Préférences:\n• Notifications : activées\n• Mode éco : activé\n• Thème : Vert sobre");
        return view;
    }
}
