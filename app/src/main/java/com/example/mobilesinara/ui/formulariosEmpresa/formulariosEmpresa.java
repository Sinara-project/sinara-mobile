package com.example.mobilesinara.ui.formulariosEmpresa;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Interface.Mongo.IPermissoes;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.FormularioPersonalizado;
import com.example.mobilesinara.Models.Permissao;
import com.example.mobilesinara.Models.campos;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentFormulariosEmpresaBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class formulariosEmpresa extends Fragment {

    private FragmentFormulariosEmpresaBinding binding;
    private String idPermissaoSelecionada = "1";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new ViewModelProvider(this).get(FormulariosEmpresaViewModel.class);
        binding = FragmentFormulariosEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Log.d("DEBUG_FORM", "onCreateView iniciado");

        Bundle args = getArguments();
        String cnpj = null;

        if (args != null && args.containsKey("cnpj")) {
            cnpj = args.getString("cnpj");
            Log.d("DEBUG_FORM", "CNPJ recebido via argumentos: " + cnpj);
        } else {
            SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", Context.MODE_PRIVATE);
            cnpj = prefs.getString("cnpj", null);
            Log.d("DEBUG_FORM", "CNPJ recuperado do SharedPreferences: " + cnpj);
        }

        if (cnpj == null || cnpj.isEmpty()) {
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            Log.e("DEBUG_FORM", "CNPJ é nulo ou vazio");
            return root;
        }

        // Referências de layout
        TextInputEditText titulo = root.findViewById(R.id.text_titulo);
        TextInputEditText descricao = root.findViewById(R.id.text_desc);
        Button btnMostrarOpcoes = root.findViewById(R.id.btnMostrarOpcoes);
        LinearLayout layoutOpcoes = root.findViewById(R.id.layoutOpcoes);
        Button btnAdicionarCampo = root.findViewById(R.id.button13);
        LinearLayout cardContainer = root.findViewById(R.id.cardContainer);
        Button btCadastrar = root.findViewById(R.id.bt_cadastrar);
        ImageView imgEmpresa = root.findViewById(R.id.imgEmpresa);

        String permissoes = "";

        // Diagnóstico dos botões
        if (btnAdicionarCampo == null) {
            Log.e("DEBUG_BTN", "btnAdicionarCampo == null (verifique ID button13 no layout)");
            Toast.makeText(getContext(), "Botão 'Adicionar Campo' não encontrado", Toast.LENGTH_LONG).show();
        } else {
            Log.d("DEBUG_BTN", "btnAdicionarCampo encontrado, configurando listener...");
        }

        if (cardContainer == null) {
            Log.e("DEBUG_BTN", "cardContainer == null (verifique ID cardContainer no layout)");
        }

        // Mostrar / ocultar opções extras
        String finalCnpj1 = cnpj;
        btnMostrarOpcoes.setOnClickListener(v -> {
            if (layoutOpcoes.getVisibility() == View.VISIBLE) {
                layoutOpcoes.setVisibility(View.GONE);
                return;
            }

            layoutOpcoes.setVisibility(View.VISIBLE);
            layoutOpcoes.removeAllViews(); // limpa opções anteriores

            // Buscar permissões via Retrofit
            IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
            Call<Empresa> callEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(finalCnpj1);

            callEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
                @Override
                public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int idEmpresa = response.body().getId();

                        IPermissoes iPermissoes = ApiClientAdapter.getRetrofitInstance().create(IPermissoes.class);
                        Call<List<Permissao>> callPermissoes = iPermissoes.getPermissaoPorEmpresa(idEmpresa);

                        callPermissoes.enqueue(new Callback<List<Permissao>>() {
                            @Override
                            public void onResponse(Call<List<Permissao>> call, Response<List<Permissao>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    List<Permissao> permissoes = response.body();

                                    for (Permissao p : permissoes) {
                                        Button btnPermissao = new Button(requireContext());
                                        btnPermissao.setText(p.getNomePermissao());
                                        btnPermissao.setAllCaps(false);
                                        btnPermissao.setTextColor(Color.WHITE);
                                        btnPermissao.setBackgroundColor(Color.parseColor("#FF8669"));
                                        btnPermissao.setPadding(16, 8, 16, 8);

                                        // Ação ao clicar
                                        btnPermissao.setOnClickListener(v2 -> {
                                            idPermissaoSelecionada = p.getId();
                                            Toast.makeText(getContext(),
                                                    "Permissão selecionada: " + p.getNomePermissao(),
                                                    Toast.LENGTH_SHORT).show();

                                            // Marcar visualmente
                                            for (int i = 0; i < layoutOpcoes.getChildCount(); i++) {
                                                View child = layoutOpcoes.getChildAt(i);
                                                if (child instanceof Button) {
                                                    child.setBackgroundColor(Color.parseColor("#FF8669"));
                                                }
                                            }
                                            btnPermissao.setBackgroundColor(Color.parseColor("#E65100"));
                                        });

                                        layoutOpcoes.addView(btnPermissao);
                                    }

                                } else {
                                    Toast.makeText(getContext(), "Nenhuma permissão encontrada", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Permissao>> call, Throwable t) {
                                Toast.makeText(getContext(), "Erro ao carregar permissões", Toast.LENGTH_SHORT).show();
                                Log.e("DEBUG_PERMISSOES", "Erro: " + t.getMessage(), t);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Empresa> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro ao buscar empresa", Toast.LENGTH_SHORT).show();
                    Log.e("DEBUG_EMPRESA", "Erro: " + t.getMessage(), t);
                }
            });
        });


        // Listener do botão "Adicionar Campo" com logs
        if (btnAdicionarCampo != null) {
            btnAdicionarCampo.setEnabled(true);
            btnAdicionarCampo.setClickable(true);

            btnAdicionarCampo.setOnClickListener(v -> {
                Log.d("DEBUG_BTN", "Botão 'Adicionar Campo' clicado!");
                Toast.makeText(getContext(), "Botão clicado!", Toast.LENGTH_SHORT).show();

                try {
                    adicionarNovoCard(inflater, cardContainer);
                } catch (Exception e) {
                    Log.e("DEBUG_BTN", "Erro ao chamar adicionarNovoCard", e);
                    Toast.makeText(getContext(), "Erro ao adicionar card: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        // Botão cadastrar
        String finalCnpj = cnpj;
        btCadastrar.setOnClickListener(v -> {
            Log.d("DEBUG_FORM", "Botão cadastrar clicado");
            List<campos> camposList = new ArrayList<>();

            for (int i = 0; i < cardContainer.getChildCount(); i++) {
                View card = cardContainer.getChildAt(i);

                EditText editNomeCampo = card.findViewById(R.id.text_desc);
                RadioButton radioObrigatorio = card.findViewById(R.id.radioButton2);
                LinearLayout opcoesContainer = card.findViewById(R.id.opcoesContainer);

                Button btnEscrita = card.findViewById(R.id.button17);
                Button btnEscolha = card.findViewById(R.id.button18);

                String tipoCampo = btnEscrita.getCurrentTextColor() == Color.WHITE ? "Escrita" : "Escolha";
                boolean obrigatorio = radioObrigatorio.isChecked();

                List<String> opcoes = new ArrayList<>();
                for (int j = 0; j < opcoesContainer.getChildCount(); j++) {
                    View opcaoView = opcoesContainer.getChildAt(j);
                    EditText editOpcao = opcaoView.findViewById(R.id.editOpcao);
                    if (editOpcao != null && !editOpcao.getText().toString().trim().isEmpty()) {
                        opcoes.add(editOpcao.getText().toString().trim());
                    }
                }

                campos campo = new campos(
                        editNomeCampo.getText().toString().trim(),
                        tipoCampo,
                        obrigatorio,
                        opcoes
                );

                camposList.add(campo);
                Log.d("DEBUG_FORM", "Campo adicionado: " + campo.getLabel() + " tipo=" + campo.getTipo());
            }

            criarFormulario(
                    titulo.getText().toString(),
                    descricao.getText().toString(),
                    camposList,
                    idPermissaoSelecionada,
                    finalCnpj
            );
        });

        chamarEmpresa(cnpj, imgEmpresa);

        return root;
    }

    private void chamarEmpresa(String cnpj, ImageView imgEmpresa) {
        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
        Call<Empresa> callEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(cnpj);
        callEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int idEmpresa = response.body().getId();
                    Log.d("DEBUG_API", "Empresa encontrada. ID = " + idEmpresa);
                    Glide.with(requireContext())
                            .load(response.body().getImageUrl())
                            .into(imgEmpresa);
                } else {
                    Toast.makeText(getContext(), "Empresa não encontrada pelo CNPJ", Toast.LENGTH_SHORT).show();
                    Log.e("DEBUG_API", "Empresa não encontrada. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Toast.makeText(getContext(), "Erro de conexão (empresa)", Toast.LENGTH_SHORT).show();
                Log.e("DEBUG_API", "Falha empresa: " + t.getMessage(), t);
            }
        });
    }

    private void criarFormulario(String titulo, String descricao, List<campos> campos, String permissoes, String cnpj) {
        Log.d("DEBUG_API", "Chamando criarFormulario com " + campos.size() + " campos");
        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
        Call<Empresa> callEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(cnpj);
        callEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int idEmpresa = response.body().getId();
                    Log.d("DEBUG_API", "Empresa encontrada. ID = " + idEmpresa);

                    IFormularioPersonalizado iFormularioPersonalizado =
                            ApiClientAdapter.getRetrofitInstance().create(IFormularioPersonalizado.class);
                    FormularioPersonalizado formularioPersonalizado = new FormularioPersonalizado(idEmpresa, titulo, descricao, campos, permissoes);

                    Call<FormularioPersonalizado> callForm =
                            iFormularioPersonalizado.InsertFormularioPersonalizado(formularioPersonalizado);

                    callForm.enqueue(new Callback<FormularioPersonalizado>() {
                        @Override
                        public void onResponse(Call<FormularioPersonalizado> call, Response<FormularioPersonalizado> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(requireContext(), "Formulário criado com sucesso!", Toast.LENGTH_SHORT).show();
                                Log.d("DEBUG_API", "Formulário criado com sucesso");
                            } else {
                                Toast.makeText(requireContext(), "Erro ao criar formulário", Toast.LENGTH_SHORT).show();
                                Log.e("DEBUG_API", "Erro response: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<FormularioPersonalizado> call, Throwable t) {
                            Toast.makeText(getContext(), "Falha de conexão (form)", Toast.LENGTH_SHORT).show();
                            Log.e("DEBUG_API", "Falha no formulário: " + t.getMessage(), t);
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Empresa não encontrada pelo CNPJ", Toast.LENGTH_SHORT).show();
                    Log.e("DEBUG_API", "Empresa não encontrada. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Toast.makeText(getContext(), "Erro de conexão (empresa)", Toast.LENGTH_SHORT).show();
                Log.e("DEBUG_API", "Falha empresa: " + t.getMessage(), t);
            }
        });
    }

    private void adicionarNovoCard(LayoutInflater inflater, LinearLayout container) {
        try {
            Log.d("DEBUG_ADD_CARD", "Tentando inflar item_card_formulario...");
            View novoCard = inflater.inflate(R.layout.item_card_formulario, container, false);

            if (novoCard == null) {
                Log.e("DEBUG_ADD_CARD", "novoCard == null");
                return;
            }

            Button btnEscrita = novoCard.findViewById(R.id.button17);
            Button btnEscolha = novoCard.findViewById(R.id.button18);
            Button btnAdicionarOpcao = novoCard.findViewById(R.id.btnAdicionarOpcao);
            LinearLayout opcoesContainer = novoCard.findViewById(R.id.opcoesContainer);

            if (btnEscrita == null || btnEscolha == null) {
                Log.e("DEBUG_ADD_CARD", "IDs dentro do card não encontrados. Verifique item_card_formulario.xml");
            }

            // Configuração inicial do card
            btnEscrita.setBackgroundColor(Color.parseColor("#FF8669"));
            btnEscrita.setTextColor(Color.WHITE);
            btnEscolha.setBackgroundColor(Color.WHITE);
            btnEscolha.setTextColor(Color.parseColor("#FF8669"));
            if (btnAdicionarOpcao != null) btnAdicionarOpcao.setVisibility(View.GONE);
            if (opcoesContainer != null) opcoesContainer.setVisibility(View.GONE);

            // Alternar tipo de campo
            btnEscrita.setOnClickListener(v -> {
                btnEscrita.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscrita.setTextColor(Color.WHITE);
                btnEscolha.setBackgroundColor(Color.WHITE);
                btnEscolha.setTextColor(Color.parseColor("#FF8669"));
                if (btnAdicionarOpcao != null) btnAdicionarOpcao.setVisibility(View.GONE);
                if (opcoesContainer != null) {
                    opcoesContainer.setVisibility(View.GONE);
                    opcoesContainer.removeAllViews();
                }
            });

            btnEscolha.setOnClickListener(v -> {
                btnEscolha.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscolha.setTextColor(Color.WHITE);
                btnEscrita.setBackgroundColor(Color.WHITE);
                btnEscrita.setTextColor(Color.parseColor("#FF8669"));
                if (btnAdicionarOpcao != null) btnAdicionarOpcao.setVisibility(View.VISIBLE);
                if (opcoesContainer != null) opcoesContainer.setVisibility(View.VISIBLE);
            });

            // Adicionar novas opções dinamicamente
            if (btnAdicionarOpcao != null) {
                btnAdicionarOpcao.setOnClickListener(v -> {
                    View novaOpcao = inflater.inflate(R.layout.item_opcao_formulario, opcoesContainer, false);
                    opcoesContainer.addView(novaOpcao);
                    Log.d("DEBUG_ADD_CARD", "Nova opção adicionada ao card");
                });
            }

            container.addView(novoCard);
            Log.d("DEBUG_ADD_CARD", "Card adicionado. Total agora: " + container.getChildCount());
            Toast.makeText(getContext(), "Card adicionado!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("DEBUG_ADD_CARD", "Erro ao adicionar card", e);
            Toast.makeText(getContext(), "Erro ao adicionar card: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
