package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginOperarioAlterarSenha2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario_alterar_senha2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputLayout textInputLayout1 = findViewById(R.id.textInputLayout1);
        TextInputEditText editTextSenha1 = findViewById(R.id.mostrar_senha_atual);
        TextInputLayout textInputLayout2 = findViewById(R.id.textInputLayout2);
        TextInputEditText editTextSenha2 = findViewById(R.id.mostrar_senha_nova);
        TextInputLayout textInputLayout3 = findViewById(R.id.textInputLayout3);
        TextInputEditText editTextSenha3 = findViewById(R.id.confirmar_senha);

        ImageButton btVoltar = findViewById(R.id.bt_voltar);

        //botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperarioAlterarSenha2.this, LoginOperarioAlterarSenha.class));
            }
        });


        final boolean[] senhaVisivel1 = {false};

        textInputLayout1.setEndIconOnClickListener(v -> {
            senhaVisivel1[0] = !senhaVisivel1[0];

            if (senhaVisivel1[0]) {
                // Mostra a senha
                editTextSenha1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                textInputLayout1.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_fechado));
            } else {
                // Esconde a senha
                editTextSenha1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                textInputLayout1.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_aberto));
            }

            // Mantém o cursor no fim
            if (editTextSenha1.getText() != null) {
                editTextSenha1.setSelection(editTextSenha1.getText().length());
            }
        });

        final boolean[] senhaVisivel2 = {false};

        textInputLayout2.setEndIconOnClickListener(v -> {
            senhaVisivel2[0] = !senhaVisivel2[0];

            if (senhaVisivel2[0]) {
                // Mostra a senha
                editTextSenha2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                textInputLayout2.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_fechado));
            } else {
                // Esconde a senha
                editTextSenha2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                textInputLayout2.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_aberto));
            }

            // Mantém o cursor no fim
            if (editTextSenha2.getText() != null) {
                editTextSenha2.setSelection(editTextSenha2.getText().length());
            }
        });

        final boolean[] senhaVisivel3 = {false};

        textInputLayout3.setEndIconOnClickListener(v -> {
            senhaVisivel3[0] = !senhaVisivel3[0];

            if (senhaVisivel3[0]) {
                // Mostra a senha
                editTextSenha3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                textInputLayout3.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_fechado));
            } else {
                // Esconde a senha
                editTextSenha3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                textInputLayout3.setEndIconDrawable(ContextCompat.getDrawable(this, R.drawable.olho_aberto));
            }

            // Mantém o cursor no fim
            if (editTextSenha3.getText() != null) {
                editTextSenha3.setSelection(editTextSenha3.getText().length());
            }
        });
        //https://chatgpt.com/share/68bcc05c-66c0-800f-8e25-4866a9628c7e
    }
}