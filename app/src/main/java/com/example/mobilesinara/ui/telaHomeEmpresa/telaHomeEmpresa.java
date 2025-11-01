package com.example.mobilesinara.ui.telaHomeEmpresa;

import android.content.Context;
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
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentTelaHomeEmpresaBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class telaHomeEmpresa extends Fragment {

    private FragmentTelaHomeEmpresaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        TelaHomeEmpresaViewModel TelaHomeEmpresaViewModel =
                new ViewModelProvider(this).get(TelaHomeEmpresaViewModel.class);

        binding = FragmentTelaHomeEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Recupera o CNPJ
        Bundle args = getArguments();
        String cnpj = null;

        if (args != null && args.containsKey("cnpj")) {
            cnpj = args.getString("cnpj");
            Log.d("TELA_HOME_EMPRESA", "CNPJ recebido via argumentos: " + cnpj);
        } else {
            // fallback: tenta pegar do SharedPreferences
            SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", Context.MODE_PRIVATE);
            cnpj = prefs.getString("cnpj", null);
            Log.d("TELA_HOME_EMPRESA", "CNPJ recuperado do SharedPreferences: " + cnpj);
        }

        if (cnpj == null || cnpj.isEmpty()) {
            Log.e("API", "CNPJ é null ou vazio! Não é possível chamar a API.");
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            return root;
        }

        // Inicializa os componentes da tela
        Button btCriarFormulario = root.findViewById(R.id.button13);
        Button btHistorico = root.findViewById(R.id.button16);
        ImageView iconEmpresa = root.findViewById(R.id.imgEmpresa);
        TextView relatoriosRegistrados = root.findViewById(R.id.relatoriosRegistrados);

        // Navegações
        btHistorico.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.navigation_notifications_empresa));

        btCriarFormulario.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.navigation_formulario_empresa));

        // Chama as APIs
        chamarApi(iconEmpresa, relatoriosRegistrados, cnpj);

        return root;
    }

    private void chamarApi(ImageView iconEmpresa, TextView relatoriosRegistrados, String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            Log.e("API", "Tentativa de chamar API com CNPJ nulo ou vazio.");
            return;
        }

        final int[] id = new int[1];
        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);

        // Busca empresa pelo CNPJ
        Call<Empresa> callGetEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(cnpj);
        callGetEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    id[0] = response.body().getId();
                    Log.d("API", "Empresa encontrada. ID: " + id[0]);
                    atualizarInfoEmpresa(iconEmpresa, relatoriosRegistrados, id[0]);
                } else {
                    Log.e("API", "Erro ao buscar empresa por CNPJ: " + response.code());
                    Toast.makeText(getContext(), "Erro ao buscar empresa", Toast.LENGTH_SHORT).show();
                    if (response.errorBody() != null) {
                        Log.e("API", "Mensagem do servidor: " + response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Log.e("API", "Falha ao buscar empresa por CNPJ", t);
                Toast.makeText(getContext(), "Falha de conexão ao buscar empresa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarInfoEmpresa(ImageView iconEmpresa, TextView relatoriosRegistrados, int idEmpresa) {
        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
        IFormularioPersonalizado iFormularioPersonalizado = ApiClientAdapter.getRetrofitInstance().create(IFormularioPersonalizado.class);

        // Busca quantidade de formulários
        Call<Integer> callQtdFormularios = iFormularioPersonalizado.getQtdFormularioPersonalizadoPorEmpresa(idEmpresa);
        callQtdFormularios.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    relatoriosRegistrados.setText(String.valueOf(response.body()));
                } else {
                    relatoriosRegistrados.setText("0");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("API", "Erro ao buscar quantidade de formulários", t);
            }
        });

        // Busca imagem da empresa
        Call<Empresa> callGetEmpresa = iEmpresa.getEmpresaPorId(idEmpresa);
        callGetEmpresa.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Glide.with(requireContext())
                            .load(response.body().getImageUrl())
                            .into(iconEmpresa);
                } else {
                    Log.e("API", "Erro de resposta ao carregar imagem: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Log.e("API", "Falha ao carregar imagem da empresa", t);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
