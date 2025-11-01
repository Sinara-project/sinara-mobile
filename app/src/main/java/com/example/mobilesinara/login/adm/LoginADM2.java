package com.example.mobilesinara.login.adm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        String cnpjRecebido = getIntent().getStringExtra("cnpj");
        Log.d("LOGIN_ADM2", "CNPJ recebido: " + cnpjRecebido);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_adm2);

        // Ajuste automático para respeitar áreas de sistema (status bar, nav bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String emailRecebido = getIntent().getStringExtra("email");

        Log.d("LOGIN_ADM2", "CNPJ recebido: " + cnpjRecebido);
        Log.d("LOGIN_ADM2", "Email recebido: " + emailRecebido);

        // Exibe o e-mail na tela
        TextView txtEmail = findViewById(R.id.mostrar_email);
        if (emailRecebido != null) {
            txtEmail.setText(emailRecebido);
        }

        // Campos de código (exemplo: autenticação de 6 dígitos)
        EditText[] edits = {
                findViewById(R.id.editText1),
                findViewById(R.id.editText2),
                findViewById(R.id.editText3),
                findViewById(R.id.editText4),
                findViewById(R.id.editText5),
                findViewById(R.id.editText6)
        };

        // Configura comportamento de foco automático entre os campos
        for (int i = 0; i < edits.length; i++) {
            int finalI = i;
            edits[i].addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0 && finalI < edits.length - 1) {
                        edits[finalI + 1].requestFocus();
                    } else if (s.length() == 0 && finalI > 0) {
                        edits[finalI - 1].requestFocus();
                    }
                }
            });
        }

        // Botão voltar
        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        btVoltar.setOnClickListener(v -> {
            startActivity(new Intent(LoginADM2.this, LoginADM.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Botão login
        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(v -> {
            if (cnpjRecebido == null || cnpjRecebido.isEmpty()) {
                Log.e("LOGIN_ADM2", "CNPJ não recebido — não é possível prosseguir");
                return;
            }

            Intent intent = new Intent(LoginADM2.this, HomeEmpresa.class);
            intent.putExtra("cnpj", cnpjRecebido);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}
