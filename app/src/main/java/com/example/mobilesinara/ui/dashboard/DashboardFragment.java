package com.example.mobilesinara.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.FormularioAcao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentDashboardBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerForms);
        List<FormularioAcao> lista = new ArrayList<>();
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Devolvido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Devolvido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Devolvido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", LocalDateTime.now(), "Devolvido"));

        FormsAdapter formsAdapter = new FormsAdapter(lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(formsAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}