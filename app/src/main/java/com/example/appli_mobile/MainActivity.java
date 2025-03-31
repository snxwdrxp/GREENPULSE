package com.example.appli_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.appli_mobile.dialogs.AboutDialogFragment;
import com.example.appli_mobile.fragments.HabitatListFragment;
import com.example.appli_mobile.fragments.MyHabitatFragment;
import com.example.appli_mobile.fragments.NotificationsFragment;
import com.example.appli_mobile.fragments.PreferencesFragment;
import com.example.appli_mobile.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

// activité principale avec navigation drawer
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    SessionManager session = new SessionManager(this);
    String themeChoice = session.getTheme();
    switch (themeChoice) {
      case "Feuille d'Automn":
        setTheme(R.style.Theme_FeuilleAutomn);
        break;
      case "Mer et Ocean":
        setTheme(R.style.Theme_MerOcean);
        break;
      case "Fleur":
        setTheme(R.style.Theme_Fleur);
        break;
      default:
        setTheme(R.style.Theme_TerreVerte);
        break;
    }

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // configuration de la toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // setup du drawer
    drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
      R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    // afficher le fragment par défaut
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new HabitatListFragment()).commit();
      navigationView.setCheckedItem(R.id.nav_habitat_list);
    }

    // Récupérer la vue du header
    View headerView = navigationView.getHeaderView(0);
    TextView tvUserEmail = headerView.findViewById(R.id.tvUserEmail);

    // Récupérer les infos de l'utilisateur
    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
    String email = sharedPreferences.getString("userEmail", "Email inconnu");

    // Mettre à jour le header avec les infos récupérées
    tvUserEmail.setText(email);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.nav_habitat_list) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new HabitatListFragment()).commit();
    } else if (id == R.id.nav_my_habitat) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new MyHabitatFragment()).commit();
    } else if (id == R.id.nav_notifications) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new NotificationsFragment()).commit();
    } else if (id == R.id.nav_preferences) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new PreferencesFragment()).commit();
    } else if (id == R.id.nav_about) {
      // afficher la boite de dialogue "à propos"
      AboutDialogFragment aboutDialog = new AboutDialogFragment();
      aboutDialog.show(getSupportFragmentManager(), "aboutDialog");
    }else if (id == R.id.deconnexion) {
      // Supprimer les données de session
      SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.clear(); // Supprime les infos de connexion
      editor.apply();

      // Rediriger vers LoginActivity
      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
      finish();
    }
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onBackPressed() {
    // si le drawer est ouvert, le fermer ; sinon, comportement normal
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
}
