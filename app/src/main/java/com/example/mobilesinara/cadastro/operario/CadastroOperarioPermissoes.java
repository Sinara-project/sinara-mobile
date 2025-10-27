package com.example.mobilesinara.cadastro.operario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobilesinara.Interface.Mongo.IPermissoes;
import com.example.mobilesinara.Models.Permissao;
import com.example.mobilesinara.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroOperarioPermissoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_operario_permissoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btVoltar = findViewById(R.id.bt_voltar3);
        Button btSalvar = findViewById(R.id.bt_salvar);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        IPermissoes iPermissoes = getRetrofit().create(IPermissoes.class);
        Call<List<Permissao>> call = iPermissoes.getPermissaoPorEmpresa("id da empresa que ainda não tem por causa do sql");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Permissao>> call, Response<List<Permissao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Permissao> lista = response.body();
                    PermissaoAdapter permissaoAdapter = new PermissaoAdapter(lista);
                    try {
                        recyclerView.setLayoutManager(new LinearLayoutManager(CadastroOperario.class.newInstance()));
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                    recyclerView.setAdapter(permissaoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Permissao>> call, Throwable t) {

            }
        });
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroOperarioPermissoes.this, CadastroOperario.class));
                overridePendingTransition(0, 0);
            }
        });
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroOperarioPermissoes.this, CadastroOperario.class));
                overridePendingTransition(0, 0);
            }
        });
    }
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://ms-sinara-mongo.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}