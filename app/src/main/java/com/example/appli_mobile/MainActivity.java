package com.example.appli_mobile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import com.example.appli_mobile.dialogs.AboutDialogFragment;
import com.example.appli_mobile.fragments.HabitatListFragment;
import com.example.appli_mobile.fragments.MyHabitatFragment;
import com.example.appli_mobile.fragments.NotificationsFragment;
import com.example.appli_mobile.fragments.PreferencesFragment;
import com.google.android.material.navigation.NavigationView;

// activité principale avec navigation drawer
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
