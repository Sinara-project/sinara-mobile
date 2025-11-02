package com.example.mobilesinara;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilesinara.databinding.ActivityHomeEmpresaBinding;
import com.example.mobilesinara.ui.telaHomeEmpresa.telaHomeEmpresa;

public class HomeEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeEmpresaBinding binding = ActivityHomeEmpresaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_empresa,
                R.id.navigation_formulario_empresa,
                R.id.navigation_notifications_empresa,
                R.id.profileEmpresa
        ).build();

        String cnpj = getIntent().getStringExtra("cnpj");
        String email = getIntent().getStringExtra("email");
        Log.d("HOME_EMPRESA", "CNPJ recebido: " + cnpj + ", Email recebido: " + email);

        SharedPreferences prefs = getSharedPreferences("sinara_prefs", MODE_PRIVATE);
        prefs.edit().putString("cnpj", cnpj).apply();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_empresa);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (savedInstanceState == null && cnpj != null) {
            Bundle bundle = new Bundle();
            bundle.putString("cnpj", cnpj);
            bundle.putString("email", email);

            navController.setGraph(R.navigation.mobile_navigation2, bundle);
        }
    }
}
