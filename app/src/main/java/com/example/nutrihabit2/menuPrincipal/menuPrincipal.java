package com.example.nutrihabit2.menuPrincipal;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import  com.example.nutrihabit2.R;

import com.example.nutrihabit2.menuPrincipal.ui.detalleComida.DetalleCom;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.EventListener;

public class menuPrincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Activity activity;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity=this;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration.Builder builder = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_seguimiento, R.id.nav_alimentos,
                R.id.nav_inicio_sesion,R.id.nav_salir);
        builder.setDrawerLayout(drawer);
        mAppBarConfiguration = builder.build();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            navigationView.getMenu().findItem(R.id.nav_inicio_sesion).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_salir).setVisible(false);
        }
        else{
            navigationView.getMenu().findItem(R.id.nav_inicio_sesion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_salir).setVisible(true);
        }
        navigationView.getMenu().findItem(R.id.nav_salir).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(activity, "Sesion cerrada!", Toast.LENGTH_SHORT)
                        .show();
                FirebaseAuth.getInstance().signOut();
                navigationView.getMenu().findItem(R.id.nav_inicio_sesion).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_salir).setVisible(false);
                return true;
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}