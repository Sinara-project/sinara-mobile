package com.example.mobilesinara.login.adm;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginADM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_adm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayoutSenha);
        TextInputEditText editTextCnpj = findViewById(R.id.text_cnpj);
        TextInputEditText editTextSenha = findViewById(R.id.text_senha);
        Button btAvancar = findViewById(R.id.bt_avancar);
        ImageButton btVoltar = findViewById(R.id.bt_voltar);

        //botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginADM.this, TelaOpcoes.class));
                overridePendingTransition(0, 0);
            }
        });

        //bt cadastrar para ir para tela de cadastro
        btAvancar.setOnClickListener(v -> {
            if(!editTextCnpj.getText().toString().isEmpty()&&!editTextSenha.getText().toString().isEmpty()){
                startActivity(new Intent(LoginADM.this, LoginADM2.class));
                overridePendingTransition(0, 0);
            }
            else{
                Toast.makeText(LoginADM.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        //senha visivel ou nao
        final boolean[] senhaVisivel = {false};

        textInputLayout.setEndIconOnClickListener(v -> {
            senhaVisivel[0] = !senhaVisivel[0];

            if (senhaVisivel[0]) {
                // Mostra a senha
                editTextSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_fechado));
            } else {
                // Esconde a senha
                editTextSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_aberto));
            }

            // Mantém o cursor no fim
            if (editTextSenha.getText() != null) {
                editTextSenha.setSelection(editTextSenha.getText().length());
            }
        });

    }
}