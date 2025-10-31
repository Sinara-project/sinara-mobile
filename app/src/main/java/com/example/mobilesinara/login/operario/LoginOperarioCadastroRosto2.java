package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

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

        Bundle info = getIntent().getExtras();

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
                if (photoUriString != null && info != null) {
                    Uri photoUri = Uri.parse(photoUriString);
                    enviarFotoParaServidor(photoUri, info);
                }

                Intent intent = new Intent(LoginOperarioCadastroRosto2.this, LoginOperarioAlterarSenha.class);
                intent.putExtras(info);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
    private void enviarFotoParaServidor(Uri photoUri, Bundle info) {
        if (photoUri == null || info == null) return;

        Integer idOperario = info.getInt("id");
        File file = new File(photoUri.getPath());

        RequestBody requestFile = RequestBody.create(file, okhttp3.MediaType.parse("image/*"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        IOperario api = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        Call<String> call = api.uploadFotoReconhecimento(idOperario, body);

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(LoginOperarioCadastroRosto2.this, "Foto enviada com sucesso!", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(LoginOperarioCadastroRosto2.this, "Falha ao enviar foto", Toast.LENGTH_SHORT).show()
                    );
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(LoginOperarioCadastroRosto2.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        });
    }
}