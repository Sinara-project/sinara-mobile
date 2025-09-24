package com.example.mobilesinara.cadastro.operario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;

public class CadastroOperario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_operario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button btCadastrar = findViewById(R.id.bt_cadastrar);
        Button btPermissoes = findViewById(R.id.bt_permissoes);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lembrete: adicionar verificação de que nenhum campo está vazio posteriormente
                startActivity(new Intent(CadastroOperario.this, CadastroOperarioSucesso.class));
            }
        });
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CadastroOperario.this, TelaOpcoes.class));
            }
        });

        //avança para as permissões
        btPermissoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroOperario.this, CadastroOperarioPermissoes.class);
                startActivity(intent);
            }
        });
    }
}