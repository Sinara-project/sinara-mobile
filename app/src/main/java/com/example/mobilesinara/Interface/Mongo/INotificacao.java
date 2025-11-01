package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.Notificacao;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface INotificacao {
    @GET("https://ms-sinara-mongo.onrender.com/notificacoes/buscar-por-empresa/{idEmpresa}")
    Call<List<Notificacao>> getNotificacaoPorEmpresa(@Path("idEmpresa") int id);
    @GET("https://ms-sinara-mongo.onrender.com/notificacoes/{id}")
    Call<Notificacao> getNotificacaoPorId(@Path("id") String id);
    @POST("https://ms-sinara-mongo.onrender.com/notificacoes/inserir")
    boolean InsertNotificacao(@Body Date data, String mensagem, String tipo, String categoria, String idEnvio);
}
