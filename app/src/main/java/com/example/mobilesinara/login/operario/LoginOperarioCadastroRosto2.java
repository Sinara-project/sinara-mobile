package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.google.android.material.imageview.ShapeableImageView;

public class LoginOperarioCadastroRosto2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario_cadastro_rosto2);

        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button cadastrar = findViewById(R.id.bt_cadastrar);
        ShapeableImageView imageView = findViewById(R.id.foto);

        String photoUriString = getIntent().getStringExtra("photoUri");
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            imageView.setImageURI(photoUri);
        }

        //botão de voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperarioCadastroRosto2.this, LoginOperarioCadastroRosto.class));
                overridePendingTransition(0, 0);
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginOperarioCadastroRosto2.this, LoginOperarioAlterarSenha.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}