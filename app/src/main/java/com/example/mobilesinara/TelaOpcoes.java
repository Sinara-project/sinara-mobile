package com.example.mobilesinara;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesinara.login.adm.LoginADM;
import com.example.mobilesinara.login.operario.LoginOperario;

public class TelaOpcoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_opcoes);

        Button btEmpresa = findViewById(R.id.bt_empresa);
        Button btOperario = findViewById(R.id.bt_operario);


        btEmpresa.setOnClickListener(v -> {
            startActivity(new Intent(TelaOpcoes.this, LoginADM.class));
            overridePendingTransition(0, 0);
        });

        btOperario.setOnClickListener(v -> {
            startActivity(new Intent(TelaOpcoes.this, LoginOperario.class));
            overridePendingTransition(0, 0);
        });
    }
}