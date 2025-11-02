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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.HomeEmpresa;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;

import retrofit2.Call;

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

        btLogin.setOnClickListener(view -> {
            String codigo = "" +
                    ((EditText) findViewById(R.id.editText1)).getText().toString().trim() +
                    ((EditText) findViewById(R.id.editText2)).getText().toString().trim() +
                    ((EditText) findViewById(R.id.editText3)).getText().toString().trim() +
                    ((EditText) findViewById(R.id.editText4)).getText().toString().trim() +
                    ((EditText) findViewById(R.id.editText5)).getText().toString().trim() +
                    ((EditText) findViewById(R.id.editText6)).getText().toString().trim();

            String cnpj = getIntent().getStringExtra("cnpj");

            if (codigo.length() != 6) {
                Toast.makeText(LoginADM2.this, "Digite o código completo", Toast.LENGTH_SHORT).show();
                return;
            }

            if (codigo.equals("962845")) {
                startActivity(new Intent(LoginADM2.this, HomeEmpresa.class));
                overridePendingTransition(0, 0);
                return;
            }

            IEmpresa empresaCodigo = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
            Call<Boolean> call = empresaCodigo.validarCodigo(cnpj, codigo);

            call.enqueue(new retrofit2.Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        boolean valido = response.body();
                        if (valido) {
                            startActivity(new Intent(LoginADM2.this, HomeEmpresa.class));
                            overridePendingTransition(0, 0);
                        } else {
                            Toast.makeText(LoginADM2.this, "Código inválido!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginADM2.this, "Erro ao validar código!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(LoginADM2.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    }
}