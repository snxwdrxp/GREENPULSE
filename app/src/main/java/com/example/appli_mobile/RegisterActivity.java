package com.example.appli_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.appli_mobile.utils.SessionManager;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

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
                    Toast.makeText(RegisterActivity.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return;
                }

                String urlString = "http://192.168.1.132/powerhome/register.php?firstname=" + username  + "&lastname=" + null
                        + "&email=" + email
                        + "&password=" + password;

                Ion.with(RegisterActivity.this)
                        .load(urlString)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                if (e != null) {
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    boolean estEnregistrer = jsonObject.getBoolean("success");

                                    if (estEnregistrer) {
                                        //redirection vers login
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException ex) {
                                    Toast.makeText(RegisterActivity.this, "Erreur JSON", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
