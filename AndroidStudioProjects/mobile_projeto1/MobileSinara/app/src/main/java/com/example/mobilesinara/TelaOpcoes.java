package com.example.mobilesinara;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.cadastro.adm.CadastroAdm;
import com.example.mobilesinara.cadastro.operario.CadastroOperario;

public class TelaOpcoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_opcoes);

        Button btEmpresa = findViewById(R.id.bt_empresa);
        Button btOperario = findViewById(R.id.bt_operario);

        btEmpresa.setOnClickListener(v -> {
            startActivity(new Intent(TelaOpcoes.this, CadastroAdm.class));
        });

        btOperario.setOnClickListener(v -> {
            startActivity(new Intent(TelaOpcoes.this, CadastroOperario.class));
        });
    }
}