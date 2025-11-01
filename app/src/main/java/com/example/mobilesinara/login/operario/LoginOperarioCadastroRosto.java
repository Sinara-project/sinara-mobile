package com.example.mobilesinara.login.operario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
    private ActivityResultLauncher<String> permissionLauncher;
    private Bundle info;
    private int idUser = -1;

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
        if (info != null) {
            if (info.containsKey("idUser")) {
                idUser = info.getInt("idUser");
            } else if (info.containsKey("id")) {
                idUser = info.getInt("id");
            }
        }

        Log.i("LoginOperarioCadastroRosto", "ID recebido: " + idUser);

        btVoltar.setOnClickListener(view -> {
            startActivity(new Intent(LoginOperarioCadastroRosto.this, LoginOperario.class));
            overridePendingTransition(0, 0);
            finish();
        });

        setupPermissionLauncher();
        setupCameraLauncher();

        btTirarFoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Centralize seu rosto na câmera", Toast.LENGTH_LONG).show();
                try {
                    openCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erro ao abrir a câmera", Toast.LENGTH_SHORT).show();
                }
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
    }

    private void setupPermissionLauncher() {
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
                        try {
                            openCamera();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Erro ao abrir a câmera", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Permissão de câmera necessária", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setupCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success != null && success) {
                        Toast.makeText(this, "Foto tirada com sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, LoginOperarioCadastroRosto2.class);
                        if (info != null) intent.putExtras(info);
                        intent.putExtra("photoUri", photoUri.toString());
                        intent.putExtra("idUser", idUser);
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
        String tempo = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String nomeArquivo = "upload_" + tempo;
        File pasta = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(nomeArquivo, ".jpg", pasta);

        photoUri = FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".provider",
                photo
        );

        cameraLauncher.launch(photoUri);
    }
}
