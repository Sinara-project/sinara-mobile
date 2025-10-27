package com.example.mobilesinara.ui.ProfileEmpresa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.databinding.FragmentProfileEmpresaBinding;

public class ProfileEmpresa extends Fragment {

    private FragmentProfileEmpresaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileEmpresaViewModel profileEmpresaViewModel =
                new ViewModelProvider(this).get(ProfileEmpresaViewModel.class);

        binding = FragmentProfileEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btDeslogar = root.findViewById(R.id.button4);
        btDeslogar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TelaOpcoes.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}