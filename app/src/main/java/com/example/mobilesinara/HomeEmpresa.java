package com.example.mobilesinara;

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
                R.id.navigation_home_operario,
                R.id.navigation_forms_operario,
                R.id.navigation_notifications_operario,
                R.id.navigation_profile_operario
        ).build();

        // Recupera o CNPJ enviado pelo LoginADM2
        String cnpj = getIntent().getStringExtra("cnpj");
        Log.d("HOME_EMPRESA", "CNPJ recebido no HomeEmpresa: " + cnpj);
        String email = getIntent().getStringExtra("email");

        if (cnpj == null) {
            Log.e("HOME_EMPRESA", "CNPJ recebido é null! Não é possível continuar.");
            return;
        }

        // Passa o CNPJ (e o e-mail, se quiser) para o fragmento telaHomeEmpresa
        Bundle bundle = new Bundle();
        bundle.putString("cnpj", cnpj);
        bundle.putString("email", email);

        telaHomeEmpresa fragment = new telaHomeEmpresa();
        fragment.setArguments(bundle);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_empresa);
        navController.setGraph(R.navigation.mobile_navigation2, bundle);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
