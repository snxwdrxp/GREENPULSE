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

// fragment pour afficher des notifications simulées
public class NotificationsFragment extends Fragment {

    private ListView listNotifications;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notifications;

    // constructeur vide
    public NotificationsFragment() {
        // rieeeeeeen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        listNotifications = view.findViewById(R.id.listNotifications);

        // simulation de quelques notif
        notifications = new ArrayList<>();
        notifications.add("Réduction de consommation détectée le 20/06 entre 19h et 20h.");
        notifications.add("Bonus éco-coin attribué pour utilisation en dehors du pic.");
        notifications.add("Malus appliqué pour dépassement du créneau consommateur.");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, notifications);
        listNotifications.setAdapter(adapter);
        return view;
    }
}
