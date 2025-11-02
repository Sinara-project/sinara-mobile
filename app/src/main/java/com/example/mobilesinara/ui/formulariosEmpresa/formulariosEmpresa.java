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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class formulariosEmpresa extends Fragment {

    private FragmentFormulariosEmpresaBinding binding;
    private String idPermissaoSelecionada = "1";
    private static final String TAG = "DEBUG_API";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new ViewModelProvider(this).get(FormulariosEmpresaViewModel.class);
        binding = FragmentFormulariosEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("DEBUG_FORM", "onCreateView iniciado");

        // 🔹 Recupera o CNPJ
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

        // Mostrar / ocultar permissões
        String finalCnpj1 = cnpj;
        btnMostrarOpcoes.setOnClickListener(v -> {
            if (layoutOpcoes.getVisibility() == View.VISIBLE) {
                layoutOpcoes.setVisibility(View.GONE);
                return;
            }

            layoutOpcoes.setVisibility(View.VISIBLE);
            layoutOpcoes.removeAllViews();

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

                                        btnPermissao.setOnClickListener(v2 -> {
                                            idPermissaoSelecionada = p.getId();
                                            Toast.makeText(getContext(),
                                                    "Permissão selecionada: " + p.getNomePermissao(),
                                                    Toast.LENGTH_SHORT).show();

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

        // Botão adicionar campo
        if (btnAdicionarCampo != null) {
            btnAdicionarCampo.setOnClickListener(v -> adicionarNovoCard(inflater, cardContainer));
        }

        // Botão cadastrar formulário
        String finalCnpj = cnpj;
        btCadastrar.setOnClickListener(v -> {
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
            }

            criarFormulario(titulo, descricao, camposList, Collections.singletonList(idPermissaoSelecionada), finalCnpj, cardContainer, layoutOpcoes);
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
                    String urlEmpresa = response.body().getImagemUrl();
                    if (urlEmpresa == null || urlEmpresa.isEmpty()) {
                        Glide.with(requireContext())
                                .load(R.drawable.profile_pic_default)
                                .circleCrop()
                                .placeholder(R.drawable.profile_pic_default)
                                .error(R.drawable.profile_pic_default)
                                .into(imgEmpresa);
                    } else {
                        Glide.with(requireContext())
                                .load(urlEmpresa)
                                .circleCrop()
                                .placeholder(R.drawable.profile_pic_default)
                                .error(R.drawable.profile_pic_default)
                                .into(imgEmpresa);
                    }
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Log.e(TAG, "Erro ao carregar empresa", t);
            }
        });
    }

    // ✅ agora com reset da tela após sucesso
    private void criarFormulario(TextInputEditText titulo, TextInputEditText descricao,
                                 List<campos> campos, List<String> permissoes,
                                 String cnpj, LinearLayout cardContainer, LinearLayout layoutOpcoes) {

        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
        Call<Empresa> callEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(cnpj);

        callEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int idEmpresa = response.body().getId();

                    IFormularioPersonalizado iFormularioPersonalizado =
                            ApiClientAdapter.getRetrofitInstance().create(IFormularioPersonalizado.class);

                    FormularioPersonalizado formularioPersonalizado =
                            new FormularioPersonalizado(idEmpresa, titulo.getText().toString(),
                                    descricao.getText().toString(), campos, permissoes);

                    Log.d(TAG, "Form body: " + new Gson().toJson(formularioPersonalizado));

                    iFormularioPersonalizado.InsertFormularioPersonalizado(formularioPersonalizado)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(requireContext(), "Formulário criado com sucesso!", Toast.LENGTH_SHORT).show();

                                        // 🔹 RESETA a tela
                                        titulo.setText("");
                                        descricao.setText("");
                                        cardContainer.removeAllViews();
                                        layoutOpcoes.setVisibility(View.GONE);
                                        idPermissaoSelecionada = "1";
                                        Log.d(TAG, "Tela resetada após sucesso");
                                    } else {
                                        try {
                                            String erro = response.errorBody() != null ? response.errorBody().string() : "(sem corpo)";
                                            Log.e(TAG, "Erro (" + response.code() + "): " + erro);
                                            Toast.makeText(requireContext(), "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Log.e(TAG, "Erro ao ler corpo de erro", e);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e(TAG, "Falha no formulário", t);
                                    Toast.makeText(requireContext(), "Falha na conexão com o servidor", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Log.e(TAG, "Empresa não encontrada. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Log.e(TAG, "Erro ao buscar empresa", t);
            }
        });
    }

    private void adicionarNovoCard(LayoutInflater inflater, LinearLayout container) {
        try {
            View novoCard = inflater.inflate(R.layout.item_card_formulario, container, false);
            Button btnEscrita = novoCard.findViewById(R.id.button17);
            Button btnEscolha = novoCard.findViewById(R.id.button18);
            Button btnAdicionarOpcao = novoCard.findViewById(R.id.btnAdicionarOpcao);
            LinearLayout opcoesContainer = novoCard.findViewById(R.id.opcoesContainer);

            btnEscrita.setBackgroundColor(Color.parseColor("#FF8669"));
            btnEscrita.setTextColor(Color.WHITE);

            btnEscolha.setOnClickListener(v -> {
                btnEscolha.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscolha.setTextColor(Color.WHITE);
                btnEscrita.setBackgroundColor(Color.WHITE);
                btnEscrita.setTextColor(Color.parseColor("#FF8669"));
                btnAdicionarOpcao.setVisibility(View.VISIBLE);
                opcoesContainer.setVisibility(View.VISIBLE);
            });

            btnEscrita.setOnClickListener(v -> {
                btnEscrita.setBackgroundColor(Color.parseColor("#FF8669"));
                btnEscrita.setTextColor(Color.WHITE);
                btnEscolha.setBackgroundColor(Color.WHITE);
                btnEscolha.setTextColor(Color.parseColor("#FF8669"));
                btnAdicionarOpcao.setVisibility(View.GONE);
                opcoesContainer.setVisibility(View.GONE);
            });

            btnAdicionarOpcao.setOnClickListener(v -> {
                View novaOpcao = inflater.inflate(R.layout.item_opcao_formulario, opcoesContainer, false);
                opcoesContainer.addView(novaOpcao);
            });

            container.addView(novoCard);
        } catch (Exception e) {
            Log.e("DEBUG_ADD_CARD", "Erro ao adicionar card", e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
