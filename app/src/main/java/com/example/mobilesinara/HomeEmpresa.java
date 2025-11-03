package com.example.mobilesinara;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilesinara.databinding.ActivityHomeEmpresaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeEmpresa extends AppCompatActivity {

    private ActivityHomeEmpresaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeEmpresaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_empresa,
                R.id.navigation_formulario_empresa,
                R.id.navigation_notifications_empresa,
                R.id.profileEmpresa
        ).build();

        Bundle info = getIntent() != null ? getIntent().getExtras() : null;
        String cnpj = null;
        if (info != null && info.containsKey("cnpj")) {
            cnpj = info.getString("cnpj");
        }

        SharedPreferences prefs = getSharedPreferences("sinara_prefs", MODE_PRIVATE);
        prefs.edit().putString("cnpj", cnpj).apply();

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_home_empresa);
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment não encontrado em R.id.nav_host_fragment_activity_home_empresa");
        }
        NavController navController = navHostFragment.getNavController();

        Bundle startArgs = new Bundle();
        if (cnpj != null) startArgs.putString("cnpj", cnpj);
        navController.setGraph(R.navigation.mobile_navigation2, startArgs);

        NavigationUI.setupWithNavController(binding.navView, navController);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home_empresa) {
                navController.popBackStack(R.id.navigation_home_empresa, false);
                return true;
            } else if (itemId == R.id.navigation_formulario_empresa) {
                navController.navigate(R.id.navigation_formulario_empresa);
                return true;
            } else if (itemId == R.id.navigation_notifications_empresa) {
                navController.navigate(R.id.navigation_notifications_empresa);
                return true;
            } else if (itemId == R.id.profileEmpresa) {
                navController.navigate(R.id.profileEmpresa);
                return true;
            }

            navController.popBackStack(itemId, false);
            if (navController.getCurrentDestination() == null ||
                    navController.getCurrentDestination().getId() != itemId) {
                navController.navigate(itemId);
            }

            return true;
        });
    }
}
