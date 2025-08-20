package com.example.mobilesinara.cadastro.adm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;

public class CadastroAdm2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_adm2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recupera o email enviado pela Intent
        String emailRecebido = getIntent().getStringExtra("emailUsuario");

        // Exibe em um TextView (no seu layout precisa ter um TextView com id txtEmail)
        TextView txtEmail = findViewById(R.id.mostrar_email);
        txtEmail.setText(emailRecebido);

        ImageButton btVoltar = findViewById(R.id.bt_voltar2);
        btVoltar.setOnClickListener(v -> {
            startActivity(new Intent(CadastroAdm2.this, CadastroAdm.class));
        });
    }
}