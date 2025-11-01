package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.net.Uri;
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

import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class LoginOperarioCadastroRosto2 extends AppCompatActivity {

    private int idUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario_cadastro_rosto2);

        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        Button cadastrar = findViewById(R.id.bt_cadastrar);
        ShapeableImageView imageView = findViewById(R.id.foto);

        // Recupera a foto
        String photoUriString = getIntent().getStringExtra("photoUri");
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            imageView.setImageURI(photoUri);
        }

        // Recupera informações do usuário
        Bundle info = getIntent().getExtras();
        if (info != null) {
            if (info.containsKey("idUser")) {
                idUser = info.getInt("idUser");
            } else if (info.containsKey("id")) {
                idUser = info.getInt("id");
            }
        }

        // botão voltar
        btVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginOperarioCadastroRosto2.this, LoginOperarioCadastroRosto.class);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        // botão cadastrar
        cadastrar.setOnClickListener(v -> {
            if (photoUriString != null && idUser != -1) {
                Uri photoUri = Uri.parse(photoUriString);
                enviarFotoParaServidor(photoUri, idUser);
            }

            Intent intent = new Intent(LoginOperarioCadastroRosto2.this, LoginOperarioAlterarSenha.class);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private File getFileFromUri(Uri uri) throws Exception {
        File file = new File(getCacheDir(), "upload_image.jpg");
        try (java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
             java.io.OutputStream outputStream = new java.io.FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }
        return file;
    }

    private void enviarFotoParaServidor(Uri photoUri, int idOperario) {
        if (photoUri == null || idOperario == -1) return;

        try {
            File file = getFileFromUri(photoUri);
            RequestBody requestFile = RequestBody.create(file, okhttp3.MediaType.parse("multipart/form-data"));
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            IOperario api = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
            Call<String> call = api.uploadFotoReconhecimento(idOperario, body);

            System.out.println("Enviando foto para servidor...");
            System.out.println("Arquivo: " + file.getAbsolutePath());
            System.out.println("ID Operário: " + idOperario);
            System.out.println("Nome do arquivo: " + file.getName());

            call.enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    System.out.println("Código HTTP: " + response.code());
                    System.out.println("Mensagem: " + response.message());
                    try {
                        if (response.errorBody() != null)
                            System.out.println("Erro: " + response.errorBody().string());
                    } catch (Exception e) { e.printStackTrace(); }

                    if (response.isSuccessful()) {
                        runOnUiThread(() ->
                                Toast.makeText(LoginOperarioCadastroRosto2.this, "Foto enviada com sucesso!", Toast.LENGTH_SHORT).show()
                        );
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(LoginOperarioCadastroRosto2.this, "Falha ao enviar foto (" + response.code() + ")", Toast.LENGTH_SHORT).show()
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
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao preparar a imagem: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
