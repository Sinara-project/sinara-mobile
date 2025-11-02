package com.example.mobilesinara.ui.notificacaoEmpresa;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.INotificacao;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Notificacao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentNotificacaoEmpresaBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class notificacaoEmpresa extends Fragment {

    private FragmentNotificacaoEmpresaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificacaoEmpresaViewModel NotificacaoEmpresaViewModel =
                new ViewModelProvider(this).get(NotificacaoEmpresaViewModel.class);

        binding = FragmentNotificacaoEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle args = getArguments();
        String cnpj = null;
        final int[] id = new int[1];

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
        RecyclerView recyclerView = root.findViewById(R.id.recyclerNotification);
        ImageView imgEmpresa = root.findViewById(R.id.imgEmpresa);
        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
        Call<Empresa> callEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(cnpj);
        callEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    id[0] = response.body().getId();
                    Glide.with(requireContext())
                            .load(response.body().getImageUrl())
                            .into(imgEmpresa);
                    INotificacao iNotificacao = ApiClientAdapter.getRetrofitInstance().create(INotificacao.class);
                    Call<List<Notificacao>> callNotificacao = iNotificacao.getNotificacaoPorEmpresa(id[0]);
                    callNotificacao.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<List<Notificacao>> call, Response<List<Notificacao>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Notificacao> lista = response.body();
                                NotificacaoAdapter notificacaoAdapter = new NotificacaoAdapter(lista);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(notificacaoAdapter);
                            } else {
                                Toast.makeText(getContext(), "Não deu certo", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Notificacao>> call, Throwable t) {
                            Toast.makeText(getContext(), "Não deu certo 2", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}