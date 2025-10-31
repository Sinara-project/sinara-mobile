package com.example.mobilesinara.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesinara.R;
import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Interface.Mongo.IRespostaFormularioPersonalizado;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Interface.SQL.IRegistroPonto;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Retrofit retrofit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle args = getArguments();
        if (args == null || !args.containsKey("idUser")) {
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
        }
        int idUser = args.getInt("idUser");

        Button botaoPonto = root.findViewById(R.id.button2);
        botaoPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_registroPonto);
                getActivity().overridePendingTransition(0, 0);
            }
        });
       
        Button bt_status = root.findViewById(R.id.button7);
        TextView formsPendentes = root.findViewById(R.id.formsPendentes);
        TextView formsRespondidos = root.findViewById(R.id.formsRespondidos);
        ImageView iconUser = root.findViewById(R.id.imgEmpresa);
        ImageView iconEmpresa = root.findViewById(R.id.imgEmpresa);
        Button bt_sinaraAi = root.findViewById(R.id.button);
        bt_sinaraAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ChatBot);
            }
        });
        Button bt_forms = root.findViewById(R.id.button3);
        bt_forms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_forms_operario);
            }
        });
        ImageView bt_configuration = root.findViewById(R.id.imageView13);
        bt_configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_configuration);
            }
        });
        chamarApi(formsPendentes, formsRespondidos, iconUser, iconEmpresa, bt_status, idUser);
        return root;
    }

    private void chamarApi(TextView formsPendentes, TextView formsRespondidos, ImageView iconUser, ImageView iconEmpresa, Button btStatus, int idUser) {
        String url = "https://ms-sinara-sql-oox0.onrender.com/api/user/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IOperario iOperario = retrofit.create(IOperario.class);
        IRegistroPonto iRegistroPonto = retrofit.create(IRegistroPonto.class);
        IRespostaFormularioPersonalizado iRespostaFormularioPersonalizado = retrofit.create(IRespostaFormularioPersonalizado.class);
        IFormularioPersonalizado iFormularioPersonalizado = retrofit.create(IFormularioPersonalizado.class);
        Call<Boolean> callStatus = iRegistroPonto.getStatusOperario(idUser);
        callStatus.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if(response.body()){
                        btStatus.setText("Online");
                    }
                    else{
                        btStatus.setText("Offline");
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        Call<Integer> callRespondidos = iRespostaFormularioPersonalizado.getQuantidadeRespostasPorUsuario(4);
        callRespondidos.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                  formsRespondidos.setText(String.valueOf(response.body()));
                  Log.e("Respondidos: ", "Chegou em respondidos com o id: "+idUser);
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        Call<Integer> callPendentes = iFormularioPersonalizado.getQtdFormulariosPendentes(idUser);
        callPendentes.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    formsPendentes.setText(String.valueOf(response.body()));
                    Log.e("Pendentes: ", "Chegou em pendentes com o id: "+idUser);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        Call<Operario> callGetOperario = iOperario.getOperarioPorId(idUser);
        callGetOperario.enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Operarios: ", "Chegou em operarios com o id: "+idUser);
                    Glide.with(getContext())
                            .load(response.body().getImageUrl())
                            .into(iconUser);
                    int idEmpresa = response.body().getIdEmpresa();
                    IEmpresa iEmpresa = retrofit.create(IEmpresa.class);
                    Call<Empresa> callGetEmpresa = iEmpresa.getEmpresaPorId(idEmpresa);
                    callGetEmpresa.enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                Log.e("Empresas: ", "Chegou em empresas com o id: "+idUser);
                                Glide.with(getContext())
                                        .load(response.body().getImageUrl())
                                        .into(iconEmpresa);
                            }
                        }
                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {

                        }
                    });
                }
                else{
                    Log.e("API", "Erro de resposta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
            }});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}