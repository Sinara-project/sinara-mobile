package com.example.mobilesinara;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mobilesinara.databinding.FragmentTelaHomeEmpresaBinding;


public class telaHomeEmpresa extends Fragment {

    private FragmentTelaHomeEmpresaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       TelaHomeEmpresaViewModel TelaHomeEmpresaViewModel =
                new ViewModelProvider(this).get(TelaHomeEmpresaViewModel.class);

        binding = FragmentTelaHomeEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btCriarFormulario = root.findViewById(R.id.button13);
        Button btHistorico = root.findViewById(R.id.button16);
        btHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_notifications_empresa);
            }
        });
        btCriarFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_formulario_empresa);
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