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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.DTO.HorasTrabalhadasResponse;
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
    private String cpfUser;
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
        TextView codEmpresaView = root.findViewById(R.id.textView11);

        Bundle args = getArguments();
        if (args != null) {
            cpfUser = args.getString("cpf", "(não encontrado)");
            Log.d("ProfileFragment", "Recebido cpf: " + cpfUser);
        } else {
            Log.e("ProfileFragment", "getArguments() veio nulo!");
        }

        btDeslogar.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TelaOpcoes.class);
            startActivity(intent);
        });

        chamarApi(horasTrabalhadas, horasPrevistas, pontosRegistrados, formsPreenchidos, estaDeFerias, img_pfp, nome, empresa, codEmpresaView);
        return root;
    }

    private void chamarApi(TextView horasTrabalhadas, TextView horasPrevistas, TextView pontosRegistrados,
                           TextView formsPreenchidos, TextView estaDeFerias, ImageView img_pfp, TextView nome,
                           TextView empresaTxt, TextView codEmpresaView) {

        IRegistroPonto iRegistroPonto = ApiClientAdapter.getRetrofitInstance().create(IRegistroPonto.class);
        IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        IRespostaFormularioPersonalizado iRespostaFormularioPersonalizado = ApiClientAdapter.getRetrofitInstance().create(IRespostaFormularioPersonalizado.class);
        Call<Integer> callIdUserComCpf = iOperario.getIdPorCpf(cpfUser);
        final int[] idOperario = {0};
        callIdUserComCpf.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                  idOperario[0] = response.body();
                  Log.e("o id do user é: ", String.valueOf(idOperario[0]));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

        Call<Integer> callGetQtdRespostas = iRespostaFormularioPersonalizado.getQuantidadeRespostasPorUsuario(idOperario[0]);
        callGetQtdRespostas.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    formsPreenchidos.setText(String.valueOf(response.body()));
                } else {
                    Log.e("API", "Erro de resposta: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
            }
        });

        Call<Integer> callGetQtdPontos = iRegistroPonto.getQuantidadeRegistroPonto(idOperario[0]);
        callGetQtdPontos.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pontosRegistrados.setText(String.valueOf(response.body()));
                } else {
                    Log.e("API", "Erro de resposta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
            }
        });

        Call<Operario> callGetOperario = iOperario.getOperarioPorId(idOperario[0]);
        callGetOperario.enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Operario operario = response.body();

                    horasPrevistas.setText(String.valueOf(operario.getHorasPrevistas()));
                    if (getContext() != null) {
                        Glide.with(getContext())
                                .load(operario.getImageUrl())
                                .into(img_pfp);
                    }
                    nome.setText(operario.getNome());

                    int idEmpresa = operario.getIdEmpresa();
                    IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
                    Call<Empresa> callGetEmpresa = iEmpresa.getEmpresaPorId(idEmpresa);
                    callGetEmpresa.enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                empresaTxt.setText(response.body().getNome());
                                codEmpresaView.setText("Código da empresa: "+response.body().getCodigo());
                                Log.d("ProfileFragment", "Empresa carregada: " + response.body().getNome());
                            } else {
                                Log.e("API", "Erro de resposta (empresa): " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {
                            Log.e("RetrofitError", "Erro empresa: " + t.getMessage(), t);
                        }
                    });

                    estaDeFerias.setText(operario.isFerias() ? "Está de férias" : "Não está de férias");
                } else {
                    Log.e("API", "Erro de resposta (operário): " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Log.e("RetrofitError", "Erro operário: " + t.getMessage(), t);
            }
        });

        Call<HorasTrabalhadasResponse> callHorasTrabalhadas = iRegistroPonto.getHorasTrabalhadas(idOperario[0]);
        callHorasTrabalhadas.enqueue(new Callback<HorasTrabalhadasResponse>() {
            @Override
            public void onResponse(Call<HorasTrabalhadasResponse> call, Response<HorasTrabalhadasResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    horasTrabalhadas.setText(response.body().getHorasTrabalhadas());
                } else {
                    Log.e("API", "Erro de resposta (horas): " + response.code());
                }
            }

            @Override
            public void onFailure(Call<HorasTrabalhadasResponse> call, Throwable t) {
                Log.e("RetrofitError", "Erro horas: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
