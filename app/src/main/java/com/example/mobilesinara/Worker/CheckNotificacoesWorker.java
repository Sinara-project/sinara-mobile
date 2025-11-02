package com.example.mobilesinara.Worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mobilesinara.Interface.Mongo.INotificacao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.adapter.ApiClientAdapter;
import com.example.mobilesinara.Models.Notificacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CheckNotificacoesWorker extends Worker {

    private static final String CHANNEL_ID = "notificacoes_novas";

    public CheckNotificacoesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("sinara_prefs", Context.MODE_PRIVATE);
        int idEmpresa = prefs.getInt("idEmpresa", -1);

        if (idEmpresa == -1) {
            return Result.success(); // sem empresa vinculada
        }

        try {
            INotificacao iNotificacao = ApiClientAdapter.getRetrofitInstance().create(INotificacao.class);
            Call<List<Notificacao>> call = iNotificacao.getNotificacaoPorEmpresa(idEmpresa);
            Response<List<Notificacao>> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                List<Notificacao> lista = response.body();
                int totalAtual = lista.size();

                int totalAnterior = prefs.getInt("total_notificacoes_" + idEmpresa, 0);

                if (totalAtual > totalAnterior && totalAnterior > 0) {
                    int novas = totalAtual - totalAnterior;
                    mostrarNotificacaoLocal(novas);
                }

                prefs.edit().putInt("total_notificacoes_" + idEmpresa, totalAtual).apply();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }

        return Result.success();
    }

    private void mostrarNotificacaoLocal(int novas) {
        NotificationManager manager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Novas Notificações",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Novas notificações!")
                .setContentText("Você tem " + novas + " novas notificações.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        manager.notify(2001, builder.build());
    }
}
