package com.example.mobilesinara.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.FormularioAcao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.cadastro.operario.Permissao;
import com.example.mobilesinara.cadastro.operario.PermissaoAdapter;
import com.example.mobilesinara.databinding.FragmentDashboardBinding;

import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //TODO: adicionar um método de pesquiisa, conectar tudo na api
        EditText txt_pesquisa = root.findViewById(R.id.text_pesquisa);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerForms);
        List<FormularioAcao> lista = new ArrayList<>();
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Devolvido"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Devolvido"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Aguardando resposta"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Respondido"));
        lista.add(new FormularioAcao("Titulo de exemplo", new Date(), "Devolvido"));
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