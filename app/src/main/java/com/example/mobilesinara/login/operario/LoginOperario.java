package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.api.OperarioService;
import com.example.mobilesinara.login.adm.LoginADM;
import com.example.mobilesinara.model.OperarioLoginRequestDTO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

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
        TextInputEditText editTextSenha = findViewById(R.id.text_senha);
        TextInputEditText editTextCpf = findViewById(R.id.text_pesquisa);
        TextInputEditText editTextEmail = findViewById(R.id.text_email);
        TextInputEditText editTextCodEmpresa = findViewById(R.id.text_cod_empresa);
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
                String email = editTextEmail.getText().toString().trim();
                String cpf = editTextCpf.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();
                String codigoEmpresa = editTextCodEmpresa.getText().toString().trim();

                if (!email.isEmpty() && !cpf.isEmpty() && !senha.isEmpty() && !codigoEmpresa.isEmpty()) {

                    OperarioLoginRequestDTO request = new OperarioLoginRequestDTO(email, cpf, senha, codigoEmpresa);
                    OperarioService service = ApiClientAdapter.getRetrofitInstance().create(OperarioService.class);

//                    Chamada do endpoint
                    Call<Boolean> call = service.loginOperario(request);

                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            int code = response.code();
                            if (response.isSuccessful() && response.body() != null) {
                                boolean sucesso = response.body();
                                if (sucesso) {
                                    Toast.makeText(LoginOperario.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginOperario.this, LoginOperarioCadastroRosto.class));
                                    overridePendingTransition(0, 0);
                                } else {
                                    Toast.makeText(LoginOperario.this, "Dados inválidos. Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                String errorBody = "";
                                try {
                                    if (response.errorBody() != null)
                                        errorBody = response.errorBody().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(LoginOperario.this,
                                        "Erro no servidor. HTTP " + code + " - " + errorBody,
                                        Toast.LENGTH_LONG).show();
                                // também log no Logcat
                                Log.e("LOGIN", "Erro http: " + code + " body: " + errorBody);
                            }
                        }


                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(LoginOperario.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    if (!editTextCpf.getText().toString().isEmpty() && !editTextEmail.getText().toString().isEmpty() && !editTextSenha.getText().toString().isEmpty() && !editTextCodEmpresa.getText().toString().isEmpty()) {
                        Bundle info = new Bundle();
                        info.putString("cpf", editTextCpf.getText().toString());
                        info.putString("email", editTextEmail.getText().toString());
                        info.putString("senha", editTextSenha.getText().toString());
                        info.putString("codEmpresa", editTextCodEmpresa.getText().toString());
                        Intent intent = new Intent(LoginOperario.this, LoginOperarioCadastroRosto.class);
                        intent.putExtras(info);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } else {
                        Toast.makeText(LoginOperario.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    }
                }


                //botão de voltar
                btVoltar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginOperario.this, TelaOpcoes.class));
                        overridePendingTransition(0, 0);
                    }
                });

                //senha visivel ou nao
                final boolean[] senhaVisivel = {false};

                textInputLayout.setEndIconOnClickListener(v -> {
                    senhaVisivel[0] = !senhaVisivel[0];

                    if (senhaVisivel[0]) {
                        // Mostra a senha
                        editTextSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(LoginOperario.this, R.drawable.olho_fechado));
                    } else {
                        // Esconde a senha
                        editTextSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(LoginOperario.this, R.drawable.olho_aberto));
                    }

                    // Mantém o cursor no fim
                    if (editTextSenha.getText() != null) {
                        editTextSenha.setSelection(editTextSenha.getText().length());
                    }
                });
            }
        });
    }
}