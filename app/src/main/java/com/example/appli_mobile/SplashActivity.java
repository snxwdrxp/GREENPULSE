package com.example.appli_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.appli_mobile.MainActivity;
import com.example.appli_mobile.R;

public class SplashActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    setContentView(R.layout.activity_splash);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        // Transition vers la MainActivity
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    }, 3000);
  }
}
