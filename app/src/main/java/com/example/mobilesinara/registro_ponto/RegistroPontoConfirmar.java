package com.example.mobilesinara.registro_ponto;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesinara.Interface.SQL.IRegistroPonto;
import com.example.mobilesinara.Models.RegistroPontoRequest;
import com.bumptech.glide.Glide;
import com.example.mobilesinara.Interface.SQL.IEmpresa;
import com.example.mobilesinara.Interface.SQL.IOperario;
import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroPontoConfirmar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroPontoConfirmar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int usuarioId;


    public RegistroPontoConfirmar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroPontoConfirmar.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroPontoConfirmar newInstance(String param1, String param2) {
        RegistroPontoConfirmar fragment = new RegistroPontoConfirmar();
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
        SharedPreferences prefs = requireContext().getSharedPreferences("sinara_prefs", getContext().MODE_PRIVATE);
        usuarioId = prefs.getInt("idUser", -1);

        if (usuarioId == -1) {
            Toast.makeText(getContext(), "Erro: usuário não identificado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_ponto_confirmar, container, false);

        TextView textViewHora = view.findViewById(R.id.textView21);
        ImageView imgUser = view.findViewById(R.id.iconUser);
        ImageView imgEmpresa = view.findViewById(R.id.iconEmpresa);
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

        Button btRegistroPonto = view.findViewById(R.id.bt_confirmar);
        Button btCancelar = view.findViewById(R.id.bt_cancelar);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registroPontoConfirmar_to_registroPonto);
            }
        });

        btRegistroPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registroPontoConfirmar_to_registroPontoSucesso);
            }
        });
        IOperario iOperario = ApiClientAdapter.getRetrofitInstance().create(IOperario.class);
        iOperario.getOperarioPorId(usuarioId).enqueue(new Callback<Operario>() {
            @Override
            public void onResponse(Call<Operario> call, Response<Operario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Operario operario = response.body();
                    String urlOperario = operario.getImagemUrl();
                    if (urlOperario == null || urlOperario.isEmpty()) {
                        Glide.with(requireContext()).load(R.drawable.profile_pic_default).into(imgUser);
                    } else {
                        Glide.with(requireContext()).load(urlOperario).circleCrop().into(imgUser);
                    }

                    // Empresa
                    int idEmpresa = operario.getIdEmpresa();
                    IEmpresa iEmpresa = ApiClientAdapter.getRetrofitInstance().create(IEmpresa.class);
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
        return view;
    }
}

//package com.example.mobilesinara.registro_ponto;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.navigation.Navigation;
//
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.mobilesinara.Interface.SQL.IRegistroPonto;
//import com.example.mobilesinara.Models.RegistroPontoRequest;
//import com.example.mobilesinara.R;
//import com.example.mobilesinara.adapter.ApiClientAdapter;
//
//import java.time.LocalDateTime;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RegistroPontoConfirmar extends Fragment {
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//    private int idUser = -1;
//
//    public RegistroPontoConfirmar() {
//        // Required empty public constructor
//    }
//
//    public static RegistroPontoConfirmar newInstance(String param1, String param2) {
//        RegistroPontoConfirmar fragment = new RegistroPontoConfirmar();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//            idUser = getArguments().getInt("idUser", -1);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_registro_ponto_confirmar, container, false);
//
//        TextView textViewHora = view.findViewById(R.id.textView21);
//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                LocalDateTime agora = LocalDateTime.now();
//                String horaAtual = String.format(Locale.getDefault(), "%02d:%02d", agora.getHour(), agora.getMinute());
//                textViewHora.setText(horaAtual);
//                handler.postDelayed(this, 1000);
//            }
//        };
//        handler.post(runnable);
//
//        Button btRegistroPonto = view.findViewById(R.id.bt_confirmar);
//        Button btCancelar = view.findViewById(R.id.bt_cancelar);
//
//        btCancelar.setOnClickListener(v ->
//                Navigation.findNavController(v).navigate(R.id.action_registroPontoConfirmar_to_registroPonto)
//        );
//
//        btRegistroPonto.setOnClickListener(v -> {
//            if (idUser == -1) {
//                Toast.makeText(getContext(), "Usuário não identificado", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            IRegistroPonto registroPontoAPI = ApiClientAdapter.getRetrofitInstance().create(IRegistroPonto.class);
//
//            // Primeiro, pegar a quantidade de registros do dia
//            registroPontoAPI.getQuantidadeRegistroPonto(idUser).enqueue(new Callback<Integer>() {
//                @Override
//                public void onResponse(Call<Integer> call, Response<Integer> responseQtd) {
//                    if (responseQtd.isSuccessful() && responseQtd.body() != null) {
//                        int qtd = responseQtd.body();
//                        LocalDateTime agora = LocalDateTime.now();
//
//                        // NÃO é mais necessário declarar entrada e saida
//                        // LocalDateTime entrada = null;
//                        // LocalDateTime saida = null;
//
//                        // Aqui você cria o objeto correto para enviar
//                        RegistroPontoRequest req;
//                        if (qtd == 0) {
//                            req = new RegistroPontoRequest(agora, null, idUser, idEmpresa); // envia só entrada
//                        } else {
//                            req = new RegistroPontoRequest(null, agora, idUser, idEmpresa); // envia só saída
//                        }
//
//                        // Chamada para inserir
//                        registroPontoAPI.inserirRegistroPonto(req).enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> responseInsert) {
//                                if (responseInsert.isSuccessful()) {
//                                    Toast.makeText(getContext(), "Ponto registrado com sucesso!", Toast.LENGTH_SHORT).show();
//                                    Navigation.findNavController(view)
//                                            .navigate(R.id.action_registroPontoConfirmar_to_registroPontoSucesso);
//                                } else {
//                                    Toast.makeText(getContext(), "Erro ao registrar ponto", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//                                Toast.makeText(getContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    } else {
//                        Toast.makeText(getContext(), "Não foi possível verificar registros do dia", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Integer> call, Throwable t) {
//                    Toast.makeText(getContext(), "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//
//        return view;
//    }
//}
