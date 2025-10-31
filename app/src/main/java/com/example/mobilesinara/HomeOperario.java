package com.example.mobilesinara;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilesinara.databinding.ActivityHomeOperarioBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeOperario extends AppCompatActivity {

    private ActivityHomeOperarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeOperarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configuração dos destinos de topo (bottom navigation)
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_operario,
                R.id.navigation_forms_operario,
                R.id.navigation_notifications_operario,
                R.id.navigation_profile_operario
        ).build();

        Bundle info = getIntent().getExtras();
        if (info == null) {
            info = new Bundle();
            info.putInt("idUser", -1); // valor de segurança
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_operario);
        navController.setGraph(R.navigation.mobile_navigation, info);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.setGraph(R.navigation.mobile_navigation, info);

        // Liga o bottom navigation ao NavController
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
