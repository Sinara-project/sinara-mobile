package com.example.mobilesinara.ui.notificacaoEmpresa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilesinara.Interface.Mongo.INotificacao;
import com.example.mobilesinara.Models.Notificacao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentNotificacaoEmpresaBinding;
import com.example.mobilesinara.ui.notifications.NotificationAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class notificacaoEmpresa extends Fragment {

    private FragmentNotificacaoEmpresaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificacaoEmpresaViewModel NotificacaoEmpresaViewModel =
                new ViewModelProvider(this).get(NotificacaoEmpresaViewModel.class);

        binding = FragmentNotificacaoEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerNotification);
        INotificacao iNotificacao = getRetrofit().create(INotificacao.class);
        Call<List<Notificacao>> call = iNotificacao.getNotificacaoPorUsuario(3);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Notificacao>> call, Response<List<Notificacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notificacao> lista = response.body();
                    NotificationAdapter notificationAdapter = new NotificationAdapter(lista);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(notificationAdapter);
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
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://ms-sinara-mongo.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}