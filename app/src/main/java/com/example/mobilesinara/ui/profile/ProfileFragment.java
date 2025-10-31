package com.example.mobilesinara.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.mobilesinara.Models.HorasTrabalhadasResponse;
import com.example.mobilesinara.Interface.Mongo.IRespostaFormularioPersonalizado;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Interface.SQL.IRegistroPonto;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Recupera o idUser das SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            Log.e("ProfileFragment", "ID do usuário não encontrado nas SharedPreferences");
            return root; // Evita seguir com o carregamento
        }

        Log.d("ProfileFragment", "Usuário logado: " + idUser);

        // Referências aos elementos da UI
        Button btDeslogar = root.findViewById(R.id.button4);
        ImageView img_pfp = root.findViewById(R.id.imageView10);
        TextView nome = root.findViewById(R.id.textView10);
        TextView formsPreenchidos = root.findViewById(R.id.formsPreenchidos);
        TextView horasTrabalhadas = root.findViewById(R.id.horasTrabalhadas);
        TextView horasPrevistas = root.findViewById(R.id.horasPrevistas);
        TextView pontosRegistrados = root.findViewById(R.id.pontosRegistrados);
        TextView estaDeFerias = root.findViewById(R.id.ferias);
        TextView empresa = root.findViewById(R.id.textView13);
        TextView codEmpresaView = root.findViewById(R.id.textView11);

        // Botão de logout
        btDeslogar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(requireContext(), TelaOpcoes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        // Chama as APIs
        chamarApi(horasTrabalhadas, horasPrevistas, pontosRegistrados, formsPreenchidos,
                estaDeFerias, img_pfp, nome, empresa, codEmpresaView, idUser);

        return root;
    }

    private void chamarApi(TextView horasTrabalhadas, TextView horasPrevistas, TextView pontosRegistrados,
                           TextView formsPreenchidos, TextView estaDeFerias, ImageView img_pfp, TextView nome,
                           TextView empresaTxt, TextView codEmpresaView, int idOperario) {

        IRegistroPonto iRegistroPonto = ApiClientAdapter.getRetrofitInstance().create(IRegistroPonto.class);
        IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        IRespostaFormularioPersonalizado iRespostaFormularioPersonalizado = ApiClientAdapter.getRetrofitInstance().create(IRespostaFormularioPersonalizado.class);

        // Quantidade de formulários preenchidos
        iRespostaFormularioPersonalizado.getQuantidadeRespostasPorUsuario(idOperario).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    formsPreenchidos.setText(String.valueOf(response.body()));
                } else {
                    Log.e("ProfileFragment", "Erro resposta (formulários): " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ProfileFragment", "Erro API (formulários): " + t.getMessage());
            }
        });

        // Quantidade de pontos registrados
        iRegistroPonto.getQuantidadeRegistroPonto(idOperario).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pontosRegistrados.setText(String.valueOf(response.body()));
                } else {
                    Log.e("ProfileFragment", "Erro resposta (pontos): " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ProfileFragment", "Erro API (pontos): " + t.getMessage());
            }
        });

        // Dados do Operário
        iOperario.getOperarioPorId(idOperario).enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Operario operario = response.body();

                    horasPrevistas.setText(String.valueOf(operario.getHorasPrevistas()));
                    nome.setText(operario.getNome());
                    estaDeFerias.setText(operario.isFerias() ? "Está de férias" : "Não está de férias");

                    if (getContext() != null) {
                        Glide.with(getContext())
                                .load(operario.getImageUrl())
                                .into(img_pfp);
                    }

                    int idEmpresa = operario.getIdEmpresa();
                    IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
                    iEmpresa.getEmpresaPorId(idEmpresa).enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Empresa emp = response.body();
                                empresaTxt.setText(emp.getNome());
                                codEmpresaView.setText("Código da empresa: " + emp.getCodigo());
                            } else {
                                Log.e("ProfileFragment", "Erro resposta (empresa): " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {
                            Log.e("ProfileFragment", "Erro API (empresa): " + t.getMessage());
                        }
                    });

                } else {
                    Log.e("ProfileFragment", "Erro resposta (operário): " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Log.e("ProfileFragment", "Erro API (operário): " + t.getMessage());
            }
        });

        // Horas trabalhadas
        iRegistroPonto.getHorasTrabalhadas(idOperario).enqueue(new Callback<HorasTrabalhadasResponse>() {
            @Override
            public void onResponse(Call<HorasTrabalhadasResponse> call, Response<HorasTrabalhadasResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    horasTrabalhadas.setText(response.body().getHorasTrabalhadas());
                } else {
                    Log.e("ProfileFragment", "Erro resposta (horas): " + response.code());
                }
            }

            @Override
            public void onFailure(Call<HorasTrabalhadasResponse> call, Throwable t) {
                Log.e("ProfileFragment", "Erro API (horas): " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
