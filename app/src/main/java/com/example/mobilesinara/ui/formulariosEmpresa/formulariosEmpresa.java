package com.example.mobilesinara.ui.formulariosEmpresa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Models.campos;
import com.example.mobilesinara.Models.FormularioPersonalizado;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentFormulariosEmpresaBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        TextInputEditText titulo = root.findViewById(R.id.text_titulo);
        TextInputEditText descricao = root.findViewById(R.id.text_desc);
        Button btnMostrarOpcoes = root.findViewById(R.id.btnMostrarOpcoes);
        LinearLayout layoutOpcoes = root.findViewById(R.id.layoutOpcoes);
        Button btnAdicionarCampo = root.findViewById(R.id.button13);
        LinearLayout cardContainer = root.findViewById(R.id.cardContainer);
        Button btCadastrar = root.findViewById(R.id.bt_cadastrar);
        List<campos> campos = new ArrayList<>();
        List<String> permissoes = new ArrayList<>();

        btnMostrarOpcoes.setOnClickListener(v -> {
            if (layoutOpcoes.getVisibility() == View.GONE) {
                layoutOpcoes.setVisibility(View.VISIBLE);
            } else {
                layoutOpcoes.setVisibility(View.GONE);
            }
        });

        btnAdicionarCampo.setOnClickListener(v -> {
            adicionarNovoCard(inflater, cardContainer, campos);
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarFormulario(titulo.getText().toString(), descricao.getText().toString() , campos, permissoes);
            }
        });

        return root;
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://ms-sinara-mongo.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void criarFormulario(String titulo, String descricao, List<campos> campos, List<String> permissoes){
        IFormularioPersonalizado iFormularioPersonalizado = getRetrofit().create(IFormularioPersonalizado.class);
        Call<FormularioPersonalizado> call = iFormularioPersonalizado.InsertFormularioPersonalizado(3, titulo, descricao, campos, permissoes);

        call.enqueue(new Callback<FormularioPersonalizado>() {
            @Override
            public void onResponse(Call<FormularioPersonalizado> call, Response<FormularioPersonalizado> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Funcionou", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Não funcionou", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FormularioPersonalizado> call, Throwable t) {
                Toast.makeText(getContext(), "Não funcionou 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adicionarNovoCard(LayoutInflater inflater, LinearLayout container, List<campos> campos) {
        View novoCard = inflater.inflate(R.layout.item_card_formulario, container, false);

        Button btnEscrita = novoCard.findViewById(R.id.button17);
        Button btnEscolha = novoCard.findViewById(R.id.button18);
        Button btnAdicionarOpcao = novoCard.findViewById(R.id.btnAdicionarOpcao);
        RadioButton isObrigatorio = novoCard.findViewById(R.id.radioButton2);
        LinearLayout opcoesContainer = novoCard.findViewById(R.id.opcoesContainer);
        TextView label = novoCard.findViewById(R.id.text_desc);
        List<String> opcoes = new ArrayList<>();
        final String[] tipo = new String[1];

        final boolean[] escritaSelecionada = {true};
        final boolean[] obrigatorio = {false};

        btnEscrita.setOnClickListener(v -> {
            if (!escritaSelecionada[0]) {
                escritaSelecionada[0] = true;
                btnEscrita.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscrita.setTextColor(Color.WHITE);
                btnEscolha.setBackgroundColor(Color.WHITE);
                btnEscolha.setTextColor(Color.parseColor("#FF8669"));

                btnAdicionarOpcao.setVisibility(View.GONE);
                opcoesContainer.setVisibility(View.GONE);
                opcoesContainer.removeAllViews();
                tipo[0] = "Escrita";
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
                tipo[0] = "Escolha";
            }
        });

        isObrigatorio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(obrigatorio[0]){
                obrigatorio[0] = false;
            }
            else{
                obrigatorio[0] = true;
            }
        });

        btnAdicionarOpcao.setOnClickListener(v -> {
            View novaOpcao = inflater.inflate(R.layout.item_opcao_formulario, opcoesContainer, false);
            opcoesContainer.addView(novaOpcao);
            opcoes.add(novaOpcao.findViewById(R.id.editOpcao).toString());
        });
        campos campo = new campos(label.toString(), tipo[0], obrigatorio[0], opcoes);
        campos.add(campo);
        container.addView(novoCard);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
