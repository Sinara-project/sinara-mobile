package com.example.mobilesinara.ui.monitoramentoRespondido;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilesinara.databinding.FragmentMonitoramentoRespondidoBinding;

public class monitoramento_respondido extends Fragment {

    private FragmentMonitoramentoRespondidoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MonitoramentoRespondidoViewModel MonitoramentoRespondidoViewModel =
                new ViewModelProvider(this).get(MonitoramentoRespondidoViewModel.class);

        binding = FragmentMonitoramentoRespondidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}