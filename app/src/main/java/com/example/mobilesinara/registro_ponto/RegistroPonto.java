package com.example.mobilesinara.registro_ponto;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.mobilesinara.R;

public class RegistroPonto extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private int usuarioId;

    public RegistroPonto() {}

    public static RegistroPonto newInstance(String param1, String param2) {
        RegistroPonto fragment = new RegistroPonto();
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
        usuarioId = getArguments().getInt("idUser", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_ponto, container, false);

        TextView textViewHora = view.findViewById(R.id.textView21);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String horaAtual = sdf.format(new Date());
                textViewHora.setText(horaAtual);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

        TextView textoRegistro = view.findViewById(R.id.registro_senha);
        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registroPonto_to_registroPontoSenha);
            }
        });

        ImageView voltar = view.findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registroPonto_to_navigation_home);
            }
        });

        Button btRegistrar = view.findViewById(R.id.bt_registrar);
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idUser", usuarioId);
                Navigation.findNavController(view)
                        .navigate(R.id.action_registroPonto_to_registroPontoCamera, bundle);
            }
        });

        return view;
    }


}
