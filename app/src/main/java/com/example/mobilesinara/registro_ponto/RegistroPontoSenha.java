package com.example.mobilesinara.registro_ponto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Models.SenhaRequest;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroPontoSenha extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private int idUser = -1;

    public RegistroPontoSenha() {
        // Required empty public constructor
    }

    public static RegistroPontoSenha newInstance(String param1, String param2) {
        RegistroPontoSenha fragment = new RegistroPontoSenha();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", getContext().MODE_PRIVATE);
        idUser = prefs.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
        }

        View view = inflater.inflate(R.layout.fragment_registro_ponto_senha, container, false);

        TextView textViewHora = view.findViewById(R.id.textView21);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LocalDateTime agora = LocalDateTime.now();
                String horaAtual = String.format(Locale.getDefault(), "%02d:%02d", agora.getHour(), agora.getMinute());
                textViewHora.setText(horaAtual);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

        TextInputEditText editTextSenha = view.findViewById(R.id.text_senha);
        TextView textoRegistroRosto = view.findViewById(R.id.txt_registro_rosto);
        Button btRegistroPonto = view.findViewById(R.id.bt_registro_ponto);
        ImageView voltar = view.findViewById(R.id.voltar);

        btRegistroPonto.setOnClickListener(v -> {
            String senhaDigitada = editTextSenha.getText().toString();

            if (senhaDigitada.isEmpty()) {
                Toast.makeText(getContext(), "Digite a senha", Toast.LENGTH_SHORT).show();
                return;
            }

            if (idUser == -1) {
                Toast.makeText(getContext(), "Usuário inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            SenhaRequest request = new SenhaRequest(idUser, senhaDigitada);

            IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);

            Call<Boolean> call = iOperario.verificarSenha(idUser, senhaDigitada);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Log.d("Retrofit", "Response code: " + response.code());
                    Log.d("Retrofit", "Response body: " + response.body());

                    if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idUser", idUser);

                        Navigation.findNavController(v)
                                .navigate(R.id.action_registroPontoSenha_to_registroPontoConfirmar, bundle);
                    } else {
                        Toast.makeText(getContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        textoRegistroRosto.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.action_registroPontoSenha_to_registroPonto)
        );

        voltar.setOnClickListener(view12 ->
                Navigation.findNavController(view12).navigate(R.id.action_registroPontoSenha_to_registroPonto2)
        );

        return view;
    }
}