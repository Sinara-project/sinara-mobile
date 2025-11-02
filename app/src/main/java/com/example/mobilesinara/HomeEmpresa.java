package com.example.mobilesinara;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
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

        // Recupera o CNPJ vindo da Intent
        Bundle info = getIntent().getExtras();
        String cnpj = null;
        if (info != null && info.containsKey("cnpj")) {
            cnpj = info.getString("cnpj");
        }

        // Salva no SharedPreferences
        SharedPreferences prefs = getSharedPreferences("sinara_prefs", MODE_PRIVATE);
        prefs.edit().putString("cnpj", cnpj).apply();

        // Obtém o NavHostFragment diretamente (forma mais segura)
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_home_empresa);
        NavController navController = navHostFragment.getNavController();

        // Define o grafo e os argumentos
        navController.setGraph(R.navigation.mobile_navigation2, info != null ? info : new Bundle());

        // Configura os destinos principais
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_empresa,
                R.id.navigation_formulario_empresa,
                R.id.navigation_notifications_empresa,
                R.id.profileEmpresa
        ).build();

        // Conecta a BottomNavigationView
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (savedInstanceState == null && cnpj != null) {
            Bundle bundle = new Bundle();
            bundle.putString("cnpj", cnpj);

            navController.setGraph(R.navigation.mobile_navigation2, bundle);
        }
    }
}
