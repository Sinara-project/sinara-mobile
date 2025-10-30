package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginOperarioEsqueciSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario_esqueci_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button btAvancar = findViewById(R.id.bt_avancar);
        TextInputLayout txtEmail = findViewById(R.id.email_txt);

        //botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperarioEsqueciSenha.this, LoginOperario.class));
                overridePendingTransition(0, 0);
            }
        });

        //botão de avançar
        btAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getEditText() != null ? txtEmail.getEditText().getText().toString() : "";

                if (email.isEmpty()) {
                    txtEmail.setError("Digite um e-mail válido");
                    return;
                } else {
                    txtEmail.setError(null);
                }

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(android.net.Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recuperação de senha");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Segue o link para redefinir sua senha: ...");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar e-mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(LoginOperarioEsqueciSenha.this, "Nenhum app de e-mail encontrado", Toast.LENGTH_SHORT).show();
                }

                Intent nextScreen = new Intent(LoginOperarioEsqueciSenha.this, LoginOperarioEsqueciSenha2.class);
                startActivity(nextScreen);
                overridePendingTransition(0, 0);
            }
        });

    }
}