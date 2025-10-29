package com.example.mobilesinara.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.IRespostaFormularioPersonalizado;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Interface.SQL.IRegistroPonto;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private Retrofit retrofit;

    //TODO: mexer na segurança da api de sql

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btDeslogar = root.findViewById(R.id.button4);
        ImageView img_pfp = root.findViewById(R.id.imageView10);
        TextView nome = root.findViewById(R.id.textView10);
        TextView formsPreenchidos = root.findViewById(R.id.formsPreenchidos);
        TextView horasTrabalhadas = root.findViewById(R.id.horasTrabalhadas);
        TextView horasPrevistas = root.findViewById(R.id.horasPrevistas);
        TextView pontosRegistrados = root.findViewById(R.id.pontosRegistrados);
        TextView estaDeFerias = root.findViewById(R.id.ferias);
        TextView empresa = root.findViewById(R.id.textView13);
        TextView codEmpresa = root.findViewById(R.id.textView11);
        btDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TelaOpcoes.class);
                startActivity(intent);
            }
        });
        chamarApi(horasTrabalhadas, horasPrevistas, pontosRegistrados, formsPreenchidos, estaDeFerias, img_pfp, nome, empresa, codEmpresa);
        return root;
    }

    private void chamarApi(TextView horasTrabalhadas, TextView horasPrevistas, TextView pontosRegistrados,
                           TextView formsPreenchidos, TextView estaDeFerias, ImageView img_pfp, TextView nome,
                           TextView empresaTxt, TextView codEmpresa) {
        String url = "https://ms-sinara-sql-oox0.onrender.com/api/user/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRegistroPonto iRegistroPonto = retrofit.create(IRegistroPonto.class);
        IOperario iOperario = retrofit.create(IOperario.class);
        IRespostaFormularioPersonalizado iRespostaFormularioPersonalizado = retrofit.create(IRespostaFormularioPersonalizado.class);
        Call<Integer> callGetQtdRespostas = iRespostaFormularioPersonalizado.getQuantidadeRespostasPorUsuario(4);
        callGetQtdRespostas.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    formsPreenchidos.setText(String.valueOf(response.body()));
                }
                else{
                    Log.e("API", "Erro de resposta: " + response.code());
                    Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Call<Integer> callGetQtdPontos = iRegistroPonto.getQuantidadeRegistroPonto(4);
        callGetQtdPontos.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pontosRegistrados.setText(String.valueOf(response.body()));
                }
                else{
                    Log.e("API", "Erro de resposta: " + response.code());
                    Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Call<Operario> callGetOperario = iOperario.getOperarioPorId(4);
        callGetOperario.enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    horasPrevistas.setText(String.valueOf(response.body().getHorasPrevistas()));
                    Glide.with(getContext())
                            .load(response.body().getImageUrl())
                            .into(img_pfp);
                    nome.setText(response.body().getNome());
                    int idEmpresa = response.body().getIdEmpresa();
                    IEmpresa iEmpresa = retrofit.create(IEmpresa.class);
                    Call<Empresa> callGetEmpresa = iEmpresa.getEmpresaPorId(idEmpresa);
                    callGetEmpresa.enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                empresaTxt.setText(response.body().getNome());
                                codEmpresa.setText("Código da empresa: "+response.body().getCodigo());
                            }
                            else{
                                Log.e("API", "Erro de resposta: " + response.code());
                                Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {
                            Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
                            Toast.makeText(getContext(), "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    if(response.body().isFerias()){
                        estaDeFerias.setText("Está de férias");
                    }
                    else{
                        estaDeFerias.setText("Não está de férias");
                    }
                }
                else{
                    Log.e("API", "Erro de resposta: " + response.code());
                    Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Call<String> callHorasTrabalhadas = iRegistroPonto.getHorasTrabalhadas(4);
        callHorasTrabalhadas.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    horasTrabalhadas.setText(response.body());
                }
                else{
                    Log.e("API", "Erro de resposta: " + response.code());
                    Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
