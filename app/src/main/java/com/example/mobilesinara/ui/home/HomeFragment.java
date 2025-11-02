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

import com.bumptech.glide.Glide;
import com.example.mobilesinara.R;
import com.example.mobilesinara.Interface.Mongo.IFormularioPersonalizado;
import com.example.mobilesinara.Interface.Mongo.IRespostaFormularioPersonalizado;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Interface.SQL.IRegistroPonto;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.adapter.ApiClientAdapter;
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
            return root;
        }
        int idUser = args.getInt("idUser");

        Button botaoPonto = root.findViewById(R.id.button2);
        botaoPonto.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_registroPonto);
            getActivity().overridePendingTransition(0, 0);
        });

        // Componentes UI
        Button bt_status = root.findViewById(R.id.button7);
        TextView formsPendentes = root.findViewById(R.id.formsPendentes);
        TextView formsRespondidos = root.findViewById(R.id.formsRespondidos);
        ImageView iconUser = root.findViewById(R.id.imgUser);
        ImageView iconEmpresa = root.findViewById(R.id.imgEmpresa);

        // Botão Sinara AI
        Button bt_sinaraAi = root.findViewById(R.id.button);
        bt_sinaraAi.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.ChatBot));

        // Botão Formulários
        Button bt_forms = root.findViewById(R.id.button3);
        bt_forms.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.navigation_forms_operario));

        // Botão Configuração
        ImageView bt_configuration = root.findViewById(R.id.imageView13);
        bt_configuration.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_configuration));

        // Chamar API para carregar dados
        chamarApi(formsPendentes, formsRespondidos, iconUser, iconEmpresa, bt_status, idUser);

        return root;
    }

    private void chamarApi(TextView formsPendentes, TextView formsRespondidos,
                           ImageView iconUser, ImageView iconEmpresa,
                           Button btStatus, int idUser) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://ms-sinara-sql-oox0.onrender.com/api/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        IRegistroPonto iRegistroPonto = ApiClientAdapter.getRetrofitInstance().create(IRegistroPonto.class);
        IRespostaFormularioPersonalizado iRespostaFormularioPersonalizado = ApiClientAdapter.getRetrofitInstance().create(IRespostaFormularioPersonalizado.class);
        IFormularioPersonalizado iFormularioPersonalizado = ApiClientAdapter.getRetrofitInstance().create(IFormularioPersonalizado.class);

        // Status Operário
        iRegistroPonto.getStatusOperario(idUser).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    btStatus.setText(response.body() ? "Online" : "Offline");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) { }
        });

        // Formulários respondidos
        iRespostaFormularioPersonalizado.getQuantidadeRespostasPorUsuario(idUser)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            formsRespondidos.setText(String.valueOf(response.body()));
                            Log.e("Respondidos", "ID: " + idUser);
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) { }
                });

        // Formulários pendentes
        iFormularioPersonalizado.getQtdFormulariosPendentes(idUser)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            formsPendentes.setText(String.valueOf(response.body()));
                            Log.e("Pendentes", "ID: " + idUser);
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) { }
                });

        // Dados do Operário
        iOperario.getOperarioPorId(idUser).enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Operario operario = response.body();
                    String urlOperario = operario.getImagemUrl();
                    if (urlOperario == null || urlOperario.isEmpty()) {
                        Glide.with(getContext()).load(R.drawable.profile_pic_default).into(iconUser);
                    } else {
                        Glide.with(getContext()).load(urlOperario).circleCrop().into(iconUser);
                    }

                    // Empresa
                    int idEmpresa = operario.getIdEmpresa();
                    IEmpresa iEmpresa = retrofit.create(IEmpresa.class);
                    iEmpresa.getEmpresaPorId(idEmpresa).enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String urlEmpresa = response.body().getImagemUrl();

                                // Carrega imagem da empresa (ou padrão)
                                if (urlEmpresa == null || urlEmpresa.isEmpty()) {
                                    Glide.with(requireContext())
                                            .load(R.drawable.profile_pic_default)
                                            .circleCrop()
                                            .placeholder(R.drawable.profile_pic_default)
                                            .error(R.drawable.profile_pic_default)
                                            .into(iconEmpresa);
                                } else {
                                    Glide.with(requireContext())
                                            .load(urlEmpresa)
                                            .circleCrop()
                                            .placeholder(R.drawable.profile_pic_default)
                                            .error(R.drawable.profile_pic_default)
                                            .into(iconEmpresa);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) { }
                    });
                } else {
                    Log.e("API", "Erro de resposta: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Operario> call, Throwable t) {
                Log.e("RetrofitError", "Erro: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
