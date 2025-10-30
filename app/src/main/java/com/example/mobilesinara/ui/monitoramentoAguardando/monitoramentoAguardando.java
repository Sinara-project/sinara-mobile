package com.example.mobilesinara.ui.monitoramentoAguardando;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentMonitoramentoAguardandoBinding;

public class monitoramentoAguardando extends Fragment {

    private FragmentMonitoramentoAguardandoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MonitoramentoAguardandoViewModel MonitoramentoAguardandoViewModel =
                new ViewModelProvider(this).get(MonitoramentoAguardandoViewModel.class);

        binding = FragmentMonitoramentoAguardandoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button bt_enviar_form = root.findViewById(R.id.button9);
        bt_enviar_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.formularioRespondido);
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