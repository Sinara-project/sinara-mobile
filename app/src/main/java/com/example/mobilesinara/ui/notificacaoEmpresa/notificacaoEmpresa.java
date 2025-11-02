package com.example.mobilesinara.ui.notificacaoEmpresa;

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
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Notificacao;
import com.example.mobilesinara.Models.Operario;
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
        RecyclerView recyclerView = root.findViewById(R.id.recyclerNotification);
        ImageView imgUser = root.findViewById(R.id.imgUser);
        ImageView imgEmpresa = root.findViewById(R.id.imgEmpresa);
        IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        Call<Operario> callOperario = iOperario.getOperarioPorId(3);
        callOperario.enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Glide.with(getContext())
                            .load(response.body().getImagemUrl())
                            .circleCrop()
                            .into(imgUser);
                    int idEmpresa = response.body().getIdEmpresa();
                    IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
                    Call<Empresa> callGetEmpresa = iEmpresa.getEmpresaPorId(idEmpresa);
                    callGetEmpresa.enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Glide.with(getContext())
                                        .load(response.body().getImagemUrl())
                                        .circleCrop()
                                        .into(imgEmpresa);
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
                }
                else{
                    Log.e("API", "Erro de resposta: " + response.code());
                    Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {

            }
        });
        INotificacao iNotificacao = ApiClientAdapter.getRetrofitInstance().create(INotificacao.class);
        Call<List<Notificacao>> call = iNotificacao.getNotificacaoPorEmpresa(456);
        call.enqueue(new Callback<>() {
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}