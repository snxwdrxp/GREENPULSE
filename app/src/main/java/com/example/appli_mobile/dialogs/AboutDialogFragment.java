package com.example.appli_mobile.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

// boite de dialogue "à propos"
public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // création de la boite de dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("À propos")
                .setMessage("GREENPULSE\nVersion 1.0\nGestion d'une résidence participative.\nDéveloppé par N.I.L")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        return builder.create();
    }
}
