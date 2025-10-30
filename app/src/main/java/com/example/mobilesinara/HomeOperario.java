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

        // 🔹 Recupera o bundle vindo da Intent
        Bundle info = getIntent().getExtras();
        if (info != null) {
            for (String key : info.keySet()) {
                System.out.println("INFO -> " + key + " = " + info.get(key));
            }
        } else {
            System.out.println("INFO -> veio nulo!");
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_operario,
                R.id.navigation_forms_operario,
                R.id.navigation_notifications_operario,
                R.id.navigation_profile_operario
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_operario);

        // 🔹 Injeta o bundle no gráfico
        navController.setGraph(R.navigation.mobile_navigation, info);

        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
