package com.example.appli_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.appli_mobile.R;
import com.example.appli_mobile.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

// fragment pour afficher les préférences et changer le thème
public class PreferencesFragment extends Fragment {

  private Spinner spinnerThemes;
  private Button btnApplyTheme;

  // constructeur vide
  public PreferencesFragment() {
    //rieeeeen
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_preferences, container, false);

    spinnerThemes = view.findViewById(R.id.spinnerThemes);
    btnApplyTheme = view.findViewById(R.id.btnApplyTheme);

    // Liste des thèmes
    List<String> themes = new ArrayList<>();
    themes.add("Terre Verte");
    themes.add("Feuille d'Automn");
    themes.add("Mer et Ocean");
    themes.add("Fleur");

    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, themes);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerThemes.setAdapter(adapter);

    // charger le theme enregistré
    SessionManager session = new SessionManager(getActivity());
    String currentTheme = session.getTheme();
    int pos = themes.indexOf(currentTheme);
    if (pos >= 0) {
      spinnerThemes.setSelection(pos);
    }

    btnApplyTheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String selectedTheme = spinnerThemes.getSelectedItem().toString();
        session.setTheme(selectedTheme);
        Toast.makeText(getActivity(), "Thème changé en " + selectedTheme + ". Redémarrage...", Toast.LENGTH_SHORT).show();
        // redemarre l'activité pour appliquer le nouveau theme
        getActivity().recreate();
      }
    });

    return view;
  }
}
