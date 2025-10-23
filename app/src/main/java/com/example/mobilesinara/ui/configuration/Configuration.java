package com.example.mobilesinara.ui.configuration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentConfigurationBinding;
import com.example.mobilesinara.login.operario.LoginOperarioAlterarSenha2;

import java.util.Map;

public class Configuration extends Fragment {
    private ActivityResultLauncher<String[]> requestPermissions;

    private FragmentConfigurationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConfigurationViewModel ConfigurationViewModel =
                new ViewModelProvider(this).get(ConfigurationViewModel.class);

        binding = FragmentConfigurationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView bt_voltar = root.findViewById(R.id.imageView3);
        Button bt_alterar_senha = root.findViewById(R.id.button12);
        Button bt_camera_permission = root.findViewById(R.id.button14);
        Button bt_sms_permission = root.findViewById(R.id.button15);
        verificarPermissaoSMS(getContext(), bt_sms_permission);
        verificarPermissaoCamera(getContext(), bt_camera_permission);
        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_home_operario);
            }
        });
        bt_alterar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginOperarioAlterarSenha2.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void verificarPermissaoCamera(Context context, Button btCameraPermission) {
        requestPermissions = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            // Handle permission requests results
            for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                String permission = entry.getKey();
                Boolean isGranted = entry.getValue();
                if (isGranted) {
                    // Permission is granted
                    Log.d("Permissions", "Permission granted: " + permission);
                } else {
                    // Permission is denied
                    Log.d("Permissions", "Permission denied: " + permission);
                }
            }
        });
    }

    private void verificarPermissaoSMS(Context context, Button bt) {
        requestPermissions = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            // Handle permission requests results
            for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                String permission = entry.getKey();
                Boolean isGranted = entry.getValue();
                if (isGranted) {
                    // Permission is granted
                    Log.d("Permissions", "Permission granted: " + permission);
                } else {
                    // Permission is denied
                    Log.d("Permissions", "Permission denied: " + permission);
                }
            }
        });
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            bt.setText("ATIVAR");
            bt.setBackgroundColor(Color.parseColor("#FF8669"));
            bt.setTextColor(Color.parseColor("#FFFFFF"));
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestPermissions.launch(new String[]{
                            Manifest.permission.SEND_SMS
                    });
                }
            });
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            bt.setText("OK");
            bt.setBackgroundColor(Color.parseColor("#7FD170"));
            bt.setTextColor(Color.parseColor("#409346"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}