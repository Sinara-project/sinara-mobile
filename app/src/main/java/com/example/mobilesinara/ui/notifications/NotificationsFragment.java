package com.example.mobilesinara.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.Models.Notificacao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerNotification);
        List<Notificacao> lista = new ArrayList<>();
        lista.add(new Notificacao("0", new Date(), "mensagem ai", "tipo ai"));
        lista.add(new Notificacao("0", new Date(), "mensagem ai", "tipo ai"));
        lista.add(new Notificacao("0", new Date(), "mensagem ai", "tipo ai"));
        lista.add(new Notificacao("0", new Date(), "mensagem ai", "tipo ai"));
        lista.add(new Notificacao("0", new Date(), "mensagem ai", "tipo ai"));
        NotificationAdapter notificationAdapter = new NotificationAdapter(lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notificationAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}