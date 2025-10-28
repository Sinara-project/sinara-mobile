package com.example.mobilesinara.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.FormularioAcao;
import com.example.mobilesinara.Interface.Mongo.IFormularioPadrao;
import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Models.FormularioPadrao;
import com.example.mobilesinara.Models.FormularioPersonalizado;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //TODO: adicionar um método de pesquiisa
        EditText txt_pesquisa = root.findViewById(R.id.text_pesquisa);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerForms);
        List<FormularioAcao> lista = new ArrayList<>();
        IFormularioPadrao iFormularioPadrao = getRetrofit().create(IFormularioPadrao.class);
        Call<List<FormularioPadrao>> callPadrao = iFormularioPadrao.getFormularioPadraoPorEmpresa(3);

        IFormularioPersonalizado iFormularioPersonalizado = getRetrofit().create(IFormularioPersonalizado.class);
        Call<List<FormularioPersonalizado>> callPersonalizado = iFormularioPersonalizado.getFormularioPersonalizadoPorEmpresa(3);

        //TODO: tanto o padrão quando o personalizado vão precisar estar na mesma recycler view
        callPadrao.enqueue(new Callback<List<FormularioPadrao>>() {
            @Override
            public void onResponse(Call<List<FormularioPadrao>> call, Response<List<FormularioPadrao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FormularioPadrao> lista = response.body();
                    FormsPadraoAdapter formsPadraoAdapter = new FormsPadraoAdapter(lista);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(formsPadraoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<FormularioPadrao>> call, Throwable t) {

            }
        });
        callPersonalizado.enqueue(new Callback<List<FormularioPersonalizado>>() {
            @Override
            public void onResponse(Call<List<FormularioPersonalizado>> call, Response<List<FormularioPersonalizado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FormularioPersonalizado> lista = response.body();
                    FormsPersonalizadoAdapter formsPersonalizadoAdapter = new FormsPersonalizadoAdapter(lista);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(formsPersonalizadoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<FormularioPersonalizado>> call, Throwable t) {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}