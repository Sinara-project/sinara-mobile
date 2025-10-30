package com.example.mobilesinara.login.operario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilesinara.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginOperarioCadastroRosto extends AppCompatActivity {

    private Uri photoUri;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private Bundle info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_operario_cadastro_rosto);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btTirarFoto = findViewById(R.id.bt_tirar_foto);
        ImageButton btVoltar = findViewById(R.id.bt_voltar);
        info = getIntent().getExtras();

        // botão de voltar
        btVoltar.setOnClickListener(view -> {
            startActivity(new Intent(LoginOperarioCadastroRosto.this, LoginOperario.class));
            overridePendingTransition(0, 0);
        });

        // inicializa o launcher da câmera
        setCamera();

        // botão tirar foto
        btTirarFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Centralize seu rosto na câmera", Toast.LENGTH_LONG).show();
            try {
                openCamera();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao abrir a câmera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCamera() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success != null && success) {
                        Toast.makeText(this, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();

                        // abre a próxima tela e envia o URI da foto
                        Intent intent = new Intent(this, LoginOperarioCadastroRosto2.class);
                        if (info != null) intent.putExtras(info);
                        intent.putExtra("photoUri", photoUri.toString());
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        Toast.makeText(this, "Foto não foi tirada", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void openCamera() throws IOException {
        // cria o arquivo temporário para salvar a imagem
        String tempo = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String nomeArquivo = "upload_" + tempo;
        File pasta = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(nomeArquivo, ".jpg", pasta);

        // gera o URI via FileProvider
        photoUri = FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".provider",
                photo
        );

        // abre a câmera
        cameraLauncher.launch(photoUri);
    }
}
