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
import androidx.activity.result.ActivityResultCallback;
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
        Bundle info = getIntent().getExtras();

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperarioCadastroRosto.this, LoginOperario.class));
                overridePendingTransition(0, 0);
            }
        });

        setCamera();

        btTirarFoto.setOnClickListener(v -> {
            Toast.makeText(LoginOperarioCadastroRosto.this, "Centralize seu rosto na câmera", Toast.LENGTH_LONG).show();
            try {
                openCamera();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao abrir a câmera", Toast.LENGTH_SHORT).show();
              }
        });
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginOperarioCadastroRosto.this, LoginOperarioCadastroRosto2.class);
                intent.putExtras(info);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setCamera() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean success) {
                        if (success != null && success) {
                            Toast.makeText(LoginOperarioCadastroRosto.this, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginOperarioCadastroRosto.this, LoginOperarioCadastroRosto2.class);
                            intent.putExtra("photoUri", photoUri.toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginOperarioCadastroRosto.this, "Foto não foi tirada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }

    private void openCamera() throws IOException {
        String tempo = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String arquivo = "upload_" + tempo;
        File pasta = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(arquivo, ".jpg", pasta);

        photoUri = FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".provider",
                photo
        );

        cameraLauncher.launch(photoUri);
    }
}