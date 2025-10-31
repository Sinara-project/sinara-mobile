package com.example.mobilesinara.login.adm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.HomeEmpresa;
import com.example.mobilesinara.R;

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

        ((EditText) findViewById(R.id.editText1)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    findViewById(R.id.editText2).requestFocus();
                }
            }
        });

        ((EditText) findViewById(R.id.editText2)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    findViewById(R.id.editText3).requestFocus();
                }
                else{
                    findViewById(R.id.editText1).requestFocus();
                }
            }
        });
        ((EditText) findViewById(R.id.editText3)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    findViewById(R.id.editText4).requestFocus();
                }
                else{
                    findViewById(R.id.editText2).requestFocus();
                }
            }
        });
        ((EditText) findViewById(R.id.editText4)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    findViewById(R.id.editText5).requestFocus();
                }
                else{
                    findViewById(R.id.editText3).requestFocus();
                }
            }
        });
        ((EditText) findViewById(R.id.editText5)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    findViewById(R.id.editText6).requestFocus();
                }
                else{
                    findViewById(R.id.editText4).requestFocus();
                }
            }
        });
        ((EditText) findViewById(R.id.editText6)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    findViewById(R.id.editText5).requestFocus();
                }
            }
        });

        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button btLogin = findViewById(R.id.bt_login);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginADM2.this, LoginADM.class));
                overridePendingTransition(0, 0);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginADM2.this, HomeEmpresa.class));
                overridePendingTransition(0, 0);
            }
        });

        // Recupera o email enviado pela Intent
        String emailRecebido = getIntent().getStringExtra("emailUsuario");

        // Exibe em um TextView (no seu layout precisa ter um TextView com id txtEmail)
        TextView txtEmail = findViewById(R.id.mostrar_email);
        txtEmail.setText(emailRecebido);
    }
}