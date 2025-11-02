package com.example.mobilesinara.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.INotificacao;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Notificacao;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentNotificationsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imgUser = root.findViewById(R.id.imgUser);
        ImageView imgEmpresa = root.findViewById(R.id.imgEmpresa);

        SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            Log.e("NotificationsFragment", "ID do usuário não encontrado nas SharedPreferences");
            return root;
        }

        Log.d("NotificationsFragment", "Usuário logado: " + idUser);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        Call<Operario> callOperario = iOperario.getOperarioPorId(idUser);

        callOperario.enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    Operario operario = response.body();

                    if (isAdded()) {
                        Glide.with(requireContext())
                                .load(operario.getImagemUrl())
                                .circleCrop()
                                .into(imgUser);
                    }

                    int idEmpresa = response.body().getIdEmpresa();
                    IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
                    iEmpresa.getEmpresaPorId(idEmpresa).enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (!isAdded()) return;
                            if (response.isSuccessful() && response.body() != null) {
                                String urlEmpresa = response.body().getImagemUrl();
                                if (urlEmpresa == null || urlEmpresa.isEmpty()) {
                                    Glide.with(requireContext())
                                            .load(R.drawable.profile_pic_default)
                                            .circleCrop()
                                            .placeholder(R.drawable.profile_pic_default)
                                            .error(R.drawable.profile_pic_default)
                                            .into(imgEmpresa);
                                } else {
                                    Glide.with(requireContext())
                                            .load(urlEmpresa)
                                            .circleCrop()
                                            .placeholder(R.drawable.profile_pic_default)
                                            .error(R.drawable.profile_pic_default)
                                            .into(imgEmpresa);
                                }
                            } else {
                                Log.e("API", "Erro empresa: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {
                            Log.e("RetrofitError", "Erro empresa: " + t.getMessage(), t);
                        }
                    });
                    Log.d("NotificationsFragment", "Empresa do usuário: " + idEmpresa);

                    INotificacao iNotificacao = ApiClientAdapter.getRetrofitInstance().create(INotificacao.class);
                    Call<List<Notificacao>> callNotificacoes = iNotificacao.getNotificacaoPorEmpresa(idEmpresa);

                    callNotificacoes.enqueue(new Callback<List<Notificacao>>() {
                        @Override
                        public void onResponse(Call<List<Notificacao>> call, Response<List<Notificacao>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Notificacao> lista = response.body();
                                NotificationAdapter notificationAdapter = new NotificationAdapter(lista);
                                recyclerView.setAdapter(notificationAdapter);
                            } else {
                                Toast.makeText(getContext(), "Nenhuma notificação encontrada", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Notificacao>> call, Throwable t) {
                            Toast.makeText(getContext(), "Erro ao carregar notificações", Toast.LENGTH_SHORT).show();
                            Log.e("NotificationsFragment", "Erro: ", t);
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Erro ao buscar dados do operário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na conexão com o servidor", Toast.LENGTH_SHORT).show();
                Log.e("NotificationsFragment", "Erro: ", t);
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
