package com.example.mobilesinara.ui.formularioRespondido;

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
import com.example.mobilesinara.databinding.FragmentFormularioRespondidoBinding;

public class FormularioRespondido extends Fragment {

    private FragmentFormularioRespondidoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FormularioRespondidoViewModel FormularioRespondidoViewModel =
                new ViewModelProvider(this).get(FormularioRespondidoViewModel.class);

        binding = FragmentFormularioRespondidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btVoltar = root.findViewById(R.id.button10);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_forms_operario);
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