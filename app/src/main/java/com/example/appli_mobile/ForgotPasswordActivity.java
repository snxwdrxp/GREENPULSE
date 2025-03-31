package com.example.appli_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

// activité pour mot de passe oublié
public class ForgotPasswordActivity extends Activity {

    EditText etEmail;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnReset = findViewById(R.id.btnReset);

        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                // simulation d'envoi d'email
                Toast.makeText(ForgotPasswordActivity.this, "lien de réinitialisation envoyé à " + email, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
