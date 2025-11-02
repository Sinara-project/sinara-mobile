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

import com.example.mobilesinara.HomeEmpresa;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.EmpresaLoginResponseDTO;
import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.Models.EmpresaLoginRequestDTO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        MaterialButton btLogin = findViewById(R.id.bt_fazer_login);
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
        btLogin.setOnClickListener(v -> {
            String cnpj = editTextCnpj.getText().toString().trim();
            String senha = editTextSenha.getText().toString().trim();

            if (!cnpj.isEmpty() && !senha.isEmpty()) {
                EmpresaLoginRequestDTO request = new EmpresaLoginRequestDTO(cnpj, senha);
                IEmpresa empresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);

                Call<Empresa> call = empresa.loginEmpresa(request);
                call.enqueue(new Callback<Empresa>() {
                    @Override
                    public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(LoginADM.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginADM.this, LoginADM2.class);
                            intent.putExtra("cnpj", cnpj);
                            intent.putExtra("email", response.body().getEmail());
                            Log.d("LOGIN_ADM", "CNPJ digitado: " + cnpj);
                Call<EmpresaLoginResponseDTO> call = empresa.loginEmpresa(request);
                call.enqueue(new Callback<EmpresaLoginResponseDTO>() {
                    @Override
                    public void onResponse(Call<EmpresaLoginResponseDTO> call, Response<EmpresaLoginResponseDTO> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            EmpresaLoginResponseDTO dados = response.body();

                            // Salva id, nome, email, imagemUrl nas SharedPreferences
                            getSharedPreferences("empresaPrefs", MODE_PRIVATE)
                                    .edit()
                                    .putLong("idEmpresa", dados.getId() == null ? -1L : dados.getId())
                                    .putString("nomeEmpresa", dados.getNome())
                                    .putString("emailEmpresa", dados.getEmail())
                                    .putString("imagemUrlEmpresa", dados.getImagemUrl())
                                    .apply();

                            Toast.makeText(LoginADM.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                            // Abre a HomeEmpresa (ou a activity que você usa após login)
                            Intent intent = new Intent(LoginADM.this, LoginADM2.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }

                    }
                    @Override
                    public void onFailure(Call<EmpresaLoginResponseDTO> call, Throwable t) {
                        Toast.makeText(LoginADM.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("LOGIN", "Falha na conexão: " + t.getMessage());

                    }
                });

            } else {
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