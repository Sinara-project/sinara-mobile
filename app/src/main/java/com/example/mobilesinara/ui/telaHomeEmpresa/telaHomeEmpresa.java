package com.example.mobilesinara.ui.telaHomeEmpresa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.example.mobilesinara.databinding.FragmentTelaHomeEmpresaBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class telaHomeEmpresa extends Fragment {

    private FragmentTelaHomeEmpresaBinding binding;
    private Retrofit retrofit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       TelaHomeEmpresaViewModel TelaHomeEmpresaViewModel =
                new ViewModelProvider(this).get(TelaHomeEmpresaViewModel.class);

        binding = FragmentTelaHomeEmpresaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btCriarFormulario = root.findViewById(R.id.button13);
        Button btHistorico = root.findViewById(R.id.button16);
        ImageView iconEmpresa = root.findViewById(R.id.imgEmpresa);
        TextView relatoriosRegistrados = root.findViewById(R.id.relatoriosRegistrados);
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
        chamarApi(iconEmpresa, relatoriosRegistrados);
        return root;
    }

    private void chamarApi(ImageView iconEmpresa, TextView relatoriosRegistrados) {
        String url = "https://ms-sinara-sql-oox0.onrender.com/api/user/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IEmpresa iEmpresa = retrofit.create(IEmpresa.class);
        Call<Empresa> callGetEmpresa = iEmpresa.getEmpresaPorId(4);
        callGetEmpresa.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Glide.with(getContext())
                            .load(response.body().getImageUrl())
                            .into(iconEmpresa);
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