package com.example.mobilesinara.login.adm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.cadastro.operario.CadastroOperario;

public class LoginADM2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_adm2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button btLogin = findViewById(R.id.bt_login);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginADM2.this, LoginADM.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginADM2.this, CadastroOperario.class));
            }
        });

        // Recupera o email enviado pela Intent
        String emailRecebido = getIntent().getStringExtra("emailUsuario");

        // Exibe em um TextView (no seu layout precisa ter um TextView com id txtEmail)
        TextView txtEmail = findViewById(R.id.mostrar_email);
        txtEmail.setText(emailRecebido);


    }
}