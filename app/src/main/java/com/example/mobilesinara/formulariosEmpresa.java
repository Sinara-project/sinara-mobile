package com.example.mobilesinara;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilesinara.databinding.FragmentFormulariosEmpresaBinding;

public class formulariosEmpresa extends Fragment {

    private FragmentFormulariosEmpresaBinding binding;
    private int contadorCards = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FormulariosEmpresaViewModel formulariosEmpresaViewModel =
                new ViewModelProvider(this).get(FormulariosEmpresaViewModel.class);

        binding = FragmentFormulariosEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Referências
        Button btnMostrarOpcoes = root.findViewById(R.id.btnMostrarOpcoes);
        LinearLayout layoutOpcoes = root.findViewById(R.id.layoutOpcoes);
        Button btnAdicionarCampo = root.findViewById(R.id.button13);
        LinearLayout cardContainer = root.findViewById(R.id.cardContainer);

        // Mostrar/ocultar opções
        btnMostrarOpcoes.setOnClickListener(v -> {
            if (layoutOpcoes.getVisibility() == View.GONE) {
                layoutOpcoes.setVisibility(View.VISIBLE);
            } else {
                layoutOpcoes.setVisibility(View.GONE);
            }
        });

        // Adicionar novo card dinamicamente
        btnAdicionarCampo.setOnClickListener(v -> {
            adicionarNovoCard(inflater, cardContainer);
        });

        return root;
    }

    private void adicionarNovoCard(LayoutInflater inflater, LinearLayout container) {
        View novoCard = inflater.inflate(R.layout.item_card_formulario, container, false);

        Button btnEscrita = novoCard.findViewById(R.id.button17);
        Button btnEscolha = novoCard.findViewById(R.id.button18);
        Button btnAdicionarOpcao = novoCard.findViewById(R.id.btnAdicionarOpcao);
        LinearLayout opcoesContainer = novoCard.findViewById(R.id.opcoesContainer);

        final boolean[] escritaSelecionada = {true};

        // Modo "Escrita"
        btnEscrita.setOnClickListener(v -> {
            if (!escritaSelecionada[0]) {
                escritaSelecionada[0] = true;
                btnEscrita.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscrita.setTextColor(Color.WHITE);
                btnEscolha.setBackgroundColor(Color.WHITE);
                btnEscolha.setTextColor(Color.parseColor("#FF8669"));

                // Esconde as opções, se houver
                btnAdicionarOpcao.setVisibility(View.GONE);
                opcoesContainer.setVisibility(View.GONE);
                opcoesContainer.removeAllViews();
            }
        });

        btnEscolha.setOnClickListener(v -> {
            if (escritaSelecionada[0]) {
                escritaSelecionada[0] = false;
                btnEscolha.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscolha.setTextColor(Color.WHITE);
                btnEscrita.setBackgroundColor(Color.WHITE);
                btnEscrita.setTextColor(Color.parseColor("#FF8669"));

                btnAdicionarOpcao.setVisibility(View.VISIBLE);
                opcoesContainer.setVisibility(View.VISIBLE);
            }
        });

        btnAdicionarOpcao.setOnClickListener(v -> {
            View novaOpcao = inflater.inflate(R.layout.item_opcao_formulario, opcoesContainer, false);
            opcoesContainer.addView(novaOpcao);
        });

        container.addView(novoCard);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
