package com.example.mobilesinara.ui.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.IFormularioPadrao;
import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.FormularioItem;
import com.example.mobilesinara.Models.FormularioPadrao;
import com.example.mobilesinara.Models.FormularioPersonalizado;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentDashboardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private EditText txtPesquisa;

    // lista completa (origem). Sempre filtramos a partir daqui.
    private final List<FormularioItem> listaUnificadaAll = new ArrayList<>();
    // adapter exibido
    private FormUnificadoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = (binding = FragmentDashboardBinding.inflate(inflater, container, false)).getRoot();

        txtPesquisa = root.findViewById(R.id.text_pesquisa);
        recyclerView = root.findViewById(R.id.recyclerForms);
        ImageView imgUser = root.findViewById(R.id.imgUser);
        ImageView imgEmpresa = root.findViewById(R.id.imgEmpresa);

        // configura RecyclerView e adapter vazio desde o início
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FormUnificadoAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // carregar fotos e formularios
        carregarDadosUsuario(imgUser, imgEmpresa);
        carregarFormularios(); // esse método adiciona itens à listaUnificadaAll e faz adapter.updateList(...)

        // pesquisa (filtra por título e status)
        txtPesquisa.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarLista(s == null ? "" : s.toString());
            }
        });

        return root;
    }

    private void carregarDadosUsuario(ImageView imgUser, ImageView imgEmpresa) {
        IOperario iOperario = getRetrofit().create(IOperario.class);
        Call<Operario> callOperario = iOperario.getOperarioPorId(4);

        callOperario.enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Operario operario = response.body();
                    Glide.with(requireContext()).load(operario.getImageUrl()).into(imgUser);

                    int idEmpresa = operario.getIdEmpresa();
                    IEmpresa iEmpresa = getRetrofit().create(IEmpresa.class);
                    iEmpresa.getEmpresaPorId(idEmpresa).enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Glide.with(requireContext()).load(response.body().getImagemUrl()).into(imgEmpresa);
                            } else {
                                Log.e("API", "Erro empresa: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {
                            Log.e("RetrofitError", "Erro empresa: " + t.getMessage(), t);
                        }
                    });
                } else {
                    Log.e("API", "Erro operario: " + (response != null ? response.code() : "nulo"));
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Log.e("RetrofitError", "Erro operario: " + t.getMessage(), t);
            }
        });
    }

    private void carregarFormularios() {
        IFormularioPadrao iFormularioPadrao = getRetrofit().create(IFormularioPadrao.class);
        IFormularioPersonalizado iFormularioPersonalizado = getRetrofit().create(IFormularioPersonalizado.class);

        iFormularioPadrao.getFormularioPadraoPorEmpresa(2).enqueue(new Callback<List<FormularioPadrao>>() {
            @Override
            public void onResponse(Call<List<FormularioPadrao>> call, Response<List<FormularioPadrao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
                    for (FormularioPadrao f : response.body()) {
                        String titulo;
                        if (f.getQualidade() != null && !f.getQualidade().trim().isEmpty()) {
                            // se qualidade existe, usa como parte do título
                            titulo = "Padrão — " + f.getQualidade();
                        } else if (f.getDataPreenchimento() != null) {
                            titulo = "Padrão — " + sdf.format(f.getDataPreenchimento());
                        } else {
                            titulo = "Formulário Padrão";
                        }
                        String status = f.getQualidade() != null ? f.getQualidade() : "Sem status";

                        FormularioItem item = new FormularioItem("padrao", titulo, status, f.getDataPreenchimento());
                        listaUnificadaAll.add(item);
                    }
                    // atualiza adapter com conteúdo completo atual
                    adapter.updateList(new ArrayList<>(listaUnificadaAll));
                } else {
                    Log.e("API", "Resposta padrao vazia ou erro: " + (response != null ? response.code() : "nulo"));
                }
            }

            @Override
            public void onFailure(Call<List<FormularioPadrao>> call, Throwable t) {
                Log.e("RetrofitError", "Erro ao buscar padrao: " + t.getMessage(), t);
            }
        });

        // PERSONALIZADO
        iFormularioPersonalizado.getFormularioPersonalizadoPorEmpresa(2).enqueue(new Callback<List<FormularioPersonalizado>>() {
            @Override
            public void onResponse(Call<List<FormularioPersonalizado>> call, Response<List<FormularioPersonalizado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (FormularioPersonalizado f : response.body()) {
                        String titulo = f.getTitulo() != null ? f.getTitulo() : "Formulário Personalizado";
                        String status = "Aguardando resposta";
                        FormularioItem item = new FormularioItem("personalizado", titulo, status, null);
                        listaUnificadaAll.add(item);
                    }
                    // atualiza adapter com conteúdo completo atual
                    adapter.updateList(new ArrayList<>(listaUnificadaAll));
                } else {
                    Log.e("API", "Resposta personalizado vazia ou erro: " + (response != null ? response.code() : "nulo"));
                }
            }

            @Override
            public void onFailure(Call<List<FormularioPersonalizado>> call, Throwable t) {
                Log.e("RetrofitError", "Erro ao buscar personalizado: " + t.getMessage(), t);
            }
        });
    }

    private void filtrarLista(String termo) {
        String t = termo == null ? "" : termo.trim().toLowerCase(Locale.ROOT);
        if (t.isEmpty()) {
            adapter.updateList(new ArrayList<>(listaUnificadaAll));
            return;
        }
        List<FormularioItem> filtrada = new ArrayList<>();
        for (FormularioItem item : listaUnificadaAll) {
            String titulo = item.getTitulo() != null ? item.getTitulo().toLowerCase(Locale.ROOT) : "";
            String status = item.getStatus() != null ? item.getStatus().toLowerCase(Locale.ROOT) : "";
            if (titulo.contains(t) || status.contains(t) || item.getTipo().toLowerCase(Locale.ROOT).contains(t)) {
                filtrada.add(item);
            }
        }
        adapter.updateList(filtrada);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://ms-sinara-mongo.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}