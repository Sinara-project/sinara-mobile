package com.example.mobilesinara.login.operario;

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

public class LoginOperarioAlterarSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario_alterar_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button btSim = findViewById(R.id.bt_sim);

        //botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginOperarioAlterarSenha.this, LoginOperarioCadastroRosto2.class));
                Intent intent = new Intent(LoginOperarioAlterarSenha.this, LoginOperarioCadastroRosto2.class);
                startActivity(intent);
                finish(); // remove a tela atual da pilha
                overridePendingTransition(0, 0);
            }
        });

        //botão sim
        btSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperarioAlterarSenha.this, LoginOperarioAlterarSenha2.class));
                overridePendingTransition(0, 0);
            }
        });

    }
}