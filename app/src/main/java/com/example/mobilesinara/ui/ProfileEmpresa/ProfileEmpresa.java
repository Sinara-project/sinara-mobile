package com.example.mobilesinara.ui.ProfileEmpresa;

import android.content.Intent;
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
import com.example.mobilesinara.databinding.FragmentProfileEmpresaBinding;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileEmpresa extends Fragment {
    private Retrofit retrofit;
    private FragmentProfileEmpresaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileEmpresaViewModel profileEmpresaViewModel =
                new ViewModelProvider(this).get(ProfileEmpresaViewModel.class);

        binding = FragmentProfileEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView icEmpresa = root.findViewById(R.id.imageView10);
        TextView nomeEmpresa = root.findViewById(R.id.textView10);
        TextView codigoEmpresa = root.findViewById(R.id.textView11);
        TextView cnpj = root.findViewById(R.id.cnpj);
        TextView email = root.findViewById(R.id.email);
        TextView ramo = root.findViewById(R.id.ramo);
        Button btDeslogar = root.findViewById(R.id.button4);
        btDeslogar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TelaOpcoes.class);
            startActivity(intent);
        });
        chamarAPI(icEmpresa, nomeEmpresa, codigoEmpresa, cnpj, email, ramo);
        return root;
    }

    private void chamarAPI(ImageView icEmpresa, TextView nomeEmpresa, TextView codigoEmpresa, TextView cnpj, TextView email, TextView ramo) {
        String url = "https://ms-sinara-sql-oox0.onrender.com/api/user/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IEmpresa iEmpresa = retrofit.create(IEmpresa.class);
        Call<Empresa> callEmpresa = iEmpresa.getEmpresaPorId(4);
        callEmpresa.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Glide.with(getContext())
                            .load(response.body().getImageUrl())
                            .into(icEmpresa);
                    nomeEmpresa.setText(response.body().getNome());
                    codigoEmpresa.setText("Código da empresa: "+response.body().getCodigo());
                    cnpj.setText(response.body().getCnpj());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}