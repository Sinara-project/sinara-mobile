package com.example.mobilesinara;

import android.content.SharedPreferences;
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

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_operario,
                R.id.navigation_forms_operario,
                R.id.navigation_notifications_operario,
                R.id.navigation_profile_operario
        ).build();

        Bundle info = getIntent().getExtras();
        int idUser = -1;
        if (info != null && info.containsKey("idUser")) {
            idUser = info.getInt("idUser");
        }

        SharedPreferences prefs = getSharedPreferences("sinara_prefs", MODE_PRIVATE);
        prefs.edit().putInt("idUser", idUser).apply();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_operario);
        navController.setGraph(R.navigation.mobile_navigation, info != null ? info : new Bundle());
        NavigationUI.setupWithNavController(binding.navView, navController);

        // ✅ Adiciona este trecho: força o bottom nav a limpar a pilha ao trocar de aba
        navView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home_operario) {
                navController.popBackStack(R.id.navigation_home_operario, false);
                return true;
            } else if (itemId == R.id.navigation_forms_operario) {
                navController.navigate(R.id.navigation_forms_operario);
                return true;
            } else if (itemId == R.id.navigation_notifications_operario) {
                navController.navigate(R.id.navigation_notifications_operario);
                return true;
            } else if (itemId == R.id.navigation_profile_operario) {
                navController.navigate(R.id.navigation_profile_operario);
                return true;
            }


            // 🔥 limpa a pilha e navega para o destino raiz
            navController.popBackStack(itemId, false);
            if (navController.getCurrentDestination() == null ||
                    navController.getCurrentDestination().getId() != itemId) {
                navController.navigate(itemId);
            }

            return true;
        });
    }
}
