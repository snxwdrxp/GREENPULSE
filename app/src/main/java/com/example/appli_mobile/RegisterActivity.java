package com.example.appli_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.appli_mobile.utils.SessionManager;

// activité d'inscription
public class RegisterActivity extends Activity {

    EditText etUsername, etEmail, etPassword, etConfirm;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialisation des vues
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirm = etConfirm.getText().toString().trim();

                if (!password.equals(confirm)) {
                    Toast.makeText(RegisterActivity.this, "les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return;
                }

                // inscription simulée
                Toast.makeText(RegisterActivity.this, "inscription réussie", Toast.LENGTH_SHORT).show();
                // auto-login après inscription
                SessionManager session = new SessionManager(RegisterActivity.this);
                session.setLogin(true);
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
