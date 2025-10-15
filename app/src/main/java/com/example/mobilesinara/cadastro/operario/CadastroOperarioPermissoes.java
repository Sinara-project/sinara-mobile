package com.example.mobilesinara.cadastro.operario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.R;

import java.util.ArrayList;
import java.util.List;

public class CadastroOperarioPermissoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_operario_permissoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btVoltar = findViewById(R.id.bt_voltar3);
        Button btSalvar = findViewById(R.id.bt_salvar);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        //TODO: trocar a lista pelo banco de dados com as permissões corretas do banco
        List<Permissao> lista = new ArrayList<>();
        lista.add(new Permissao("Permissão exemplo1", false));
        lista.add(new Permissao("Permissão exemplo2", false));
        lista.add(new Permissao("Permissão exemplo3", false));
        lista.add(new Permissao("Permissão exemplo4", false));
        PermissaoAdapter permissaoAdapter = new PermissaoAdapter(lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(permissaoAdapter);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroOperarioPermissoes.this, CadastroOperario.class));
                overridePendingTransition(0, 0);
            }
        });
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroOperarioPermissoes.this, CadastroOperario.class));
                overridePendingTransition(0, 0);
            }
        });
    }
}