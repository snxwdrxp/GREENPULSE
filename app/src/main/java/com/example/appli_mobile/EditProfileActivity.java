package com.example.appli_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// activité pour éditer le profil
public class EditProfileActivity extends Activity {

    EditText etName, etEmail;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);

        // chargement d'infos fictives
        etName.setText("faiza lpb");
        etEmail.setText("faizalpb@example.com");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // simulation de sauvegarde
                Toast.makeText(EditProfileActivity.this, "profil mis à jour", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
