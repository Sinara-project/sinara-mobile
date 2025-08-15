package com.example.mobilesinara;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(this::abrirTelaInicial, 3500);
    }

    private void abrirTelaInicial() {
        Intent rota = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(rota);
        finish();
    }
}
