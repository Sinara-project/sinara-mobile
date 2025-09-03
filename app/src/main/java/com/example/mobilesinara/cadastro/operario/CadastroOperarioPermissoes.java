package com.example.mobilesinara.cadastro.operario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;

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
        ImageButton bt_voltar = findViewById(R.id.bt_voltar3);
        Button bt_salvar = findViewById(R.id.bt_salvar);
        CheckBox opc1 = findViewById(R.id.formsCap);
        CheckBox opc2 = findViewById(R.id.formsCap2);
        CheckBox opc3 = findViewById(R.id.formsCap3);
        CheckBox opc4 = findViewById(R.id.formsCap4);
        CheckBox opc5 = findViewById(R.id.formsCap5);
        CheckBox opc6 = findViewById(R.id.formsCap6);

        opc1.setOnCheckedChangeListener((compoundButton, b) ->
                Toast.makeText(CadastroOperarioPermissoes.this, "O teste deu certo", Toast.LENGTH_SHORT).show());
        opc2.setOnCheckedChangeListener((compoundButton, b) ->
                Toast.makeText(CadastroOperarioPermissoes.this, "O teste deu certo", Toast.LENGTH_SHORT).show());
        opc3.setOnCheckedChangeListener((compoundButton, b) ->
                Toast.makeText(CadastroOperarioPermissoes.this, "O teste deu certo", Toast.LENGTH_SHORT).show());
        opc4.setOnCheckedChangeListener((compoundButton, b) ->
                Toast.makeText(CadastroOperarioPermissoes.this, "O teste deu certo", Toast.LENGTH_SHORT).show());
        opc5.setOnCheckedChangeListener((compoundButton, b) ->
                Toast.makeText(CadastroOperarioPermissoes.this, "O teste deu certo", Toast.LENGTH_SHORT).show());
        opc6.setOnCheckedChangeListener((compoundButton, b) ->
                Toast.makeText(CadastroOperarioPermissoes.this, "O teste deu certo", Toast.LENGTH_SHORT).show());
        
        bt_voltar.setOnClickListener(view ->
                startActivity(new Intent(CadastroOperarioPermissoes.this, CadastroOperario.class)));
        bt_salvar.setOnClickListener(view ->
                startActivity(new Intent(CadastroOperarioPermissoes.this, CadastroOperario.class)));
    }
}