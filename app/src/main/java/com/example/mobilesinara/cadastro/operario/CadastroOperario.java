package com.example.mobilesinara.cadastro.operario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
        EditText txtNome = findViewById(R.id.text_senha);
        EditText txtCpf = findViewById(R.id.text_cpf);
        EditText txtEmail = findViewById(R.id.text_email);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNome.getText().toString().isEmpty()&&!txtCpf.getText().toString().isEmpty()&&!txtEmail.getText().toString().isEmpty()){
                    startActivity(new Intent(CadastroOperario.this, CadastroOperarioSucesso.class));
                    overridePendingTransition(0, 0);
                }
                else{
                    Toast.makeText(CadastroOperario.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CadastroOperario.this, TelaOpcoes.class));
                overridePendingTransition(0, 0);
            }
        });

        //avança para as permissões
        btPermissoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroOperario.this, CadastroOperarioPermissoes.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}