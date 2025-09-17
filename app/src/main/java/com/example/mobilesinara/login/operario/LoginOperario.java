package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.login.adm.LoginADM;
import com.example.mobilesinara.login.adm.LoginADM2;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginOperario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout5);
        TextInputEditText editTextSenha = findViewById(R.id.mostrar_senha);
        Button login = findViewById(R.id.bt_fazer_login);
        TextView esqueciSenha = findViewById(R.id.esqueci_minha_senha);

        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperario.this, LoginOperarioEsqueciSenha.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperario.this, LoginOperarioCadastroRosto.class));
            }
        });

        //botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperario.this, TelaOpcoes.class));
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