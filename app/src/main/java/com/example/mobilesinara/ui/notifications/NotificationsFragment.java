package com.example.mobilesinara.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.Interface.Mongo.INotificacao;
import com.example.mobilesinara.Models.Notificacao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentNotificationsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerNotification);
        INotificacao iNotificacao = ApiClientAdapter.getRetrofitInstance().create(INotificacao.class);
        Call<List<Notificacao>> call = iNotificacao.getNotificacaoPorUsuario(3);
        call.enqueue(new Callback<List<Notificacao>>() {
            @Override
            public void onResponse(Call<List<Notificacao>> call, Response<List<Notificacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notificacao> lista = response.body();
                    NotificationAdapter notificationAdapter = new NotificationAdapter(lista);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(notificationAdapter);
                }
                else{
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