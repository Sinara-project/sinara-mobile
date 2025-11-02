package com.example.mobilesinara.ui.ProfileEmpresa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.R;
import com.example.mobilesinara.TelaOpcoes;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.databinding.FragmentProfileEmpresaBinding;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEmpresa extends Fragment {
    private FragmentProfileEmpresaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileEmpresaViewModel profileEmpresaViewModel =
                new ViewModelProvider(this).get(ProfileEmpresaViewModel.class);

        binding = FragmentProfileEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle args = getArguments();
        String cnpj = null;

        if (args != null && args.containsKey("cnpj")) {
            cnpj = args.getString("cnpj");
            Log.d("TELA_HOME_EMPRESA", "CNPJ recebido via argumentos: " + cnpj);
        } else {
            // fallback: tenta pegar do SharedPreferences
            SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", Context.MODE_PRIVATE);
            cnpj = prefs.getString("cnpj", null);
            Log.d("TELA_HOME_EMPRESA", "CNPJ recuperado do SharedPreferences: " + cnpj);
        }

        if (cnpj == null || cnpj.isEmpty()) {
            Log.e("API", "CNPJ é null ou vazio! Não é possível chamar a API.");
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
            return root;
        }
        ImageView icEmpresa = root.findViewById(R.id.imageView10);
        TextView nomeEmpresa = root.findViewById(R.id.textView10);
        TextView codigoEmpresa = root.findViewById(R.id.textView11);
        TextView cnpjTxt = root.findViewById(R.id.cnpj);
        TextView email = root.findViewById(R.id.email);
        TextView ramo = root.findViewById(R.id.ramo);
        Button btDeslogar = root.findViewById(R.id.button4);
        btDeslogar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TelaOpcoes.class);
            startActivity(intent);
        });
        cnpjTxt.setText(cnpj);
        chamarAPI(icEmpresa, nomeEmpresa, codigoEmpresa, cnpj, email, ramo);
        return root;
    }

    private void chamarAPI(ImageView icEmpresa, TextView nomeEmpresa, TextView codigoEmpresa, String cnpj, TextView email, TextView ramo) {
        final int[] id = new int[1];
        IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
        Call<Empresa> callEmpresaPorCnpj = iEmpresa.getEmpresaPorCnpj(cnpj);
        callEmpresaPorCnpj.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if(response.isSuccessful() && response.body() != null) {
                    id[0] = response.body().getId();
                    Call<Empresa> callEmpresa = iEmpresa.getEmpresaPorId(id[0]);
                    callEmpresa.enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                Glide.with(requireContext())
                                        .load(response.body().getImageUrl())
                                        .into(icEmpresa);
                                nomeEmpresa.setText(response.body().getNome());
                                codigoEmpresa.setText("Código da empresa: "+response.body().getCodigo());
                                email.setText(response.body().getEmail());
                                ramo.setText(response.body().getRamoAtuacao());
                            }
                            else{
                                Log.e("API", "Erro de resposta: " + response.code());
                                Toast.makeText(getContext(), "Falha: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}