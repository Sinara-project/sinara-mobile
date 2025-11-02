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

public class HomeEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeEmpresaBinding binding = ActivityHomeEmpresaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String cnpj = getIntent().getStringExtra("cnpj");
        String email = getIntent().getStringExtra("email");

        if (cnpj == null) {
            Log.e("HOME_EMPRESA", "CNPJ recebido é null! Não é possível continuar.");
            return;
        }

        Log.d("HOME_EMPRESA", "CNPJ recebido no HomeEmpresa: " + cnpj);

        // Salva o CNPJ para os fragments poderem acessar
        SharedPreferences prefs = getSharedPreferences("sinara_prefs", MODE_PRIVATE);
        prefs.edit().putString("cnpj", cnpj).apply();

        // Configuração correta da navegação
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_empresa,
                R.id.navigation_formulario_empresa,
                R.id.navigation_notifications_empresa,
                R.id.profileEmpresa
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_empresa);

        // ⚠️ NÃO use navController.setGraph(...) aqui!
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
