package com.example.mobilesinara;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilesinara.databinding.ActivityHomeEmpresaBinding;

public class HomeEmpresa extends AppCompatActivity {

    private ActivityHomeEmpresaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeEmpresaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_empresa, R.id.navigation_formulario_empresa, R.id.navigation_notifications_empresa, R.id.profileEmpresa)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_empresa);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}