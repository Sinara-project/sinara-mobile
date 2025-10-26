package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.RespostaFormularioPersonalizado;
import com.example.mobilesinara.Models.Respostas;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRespostaFormularioPersonalizado {
    @GET("https://ms-sinara-mongo.onrender.com/resposta-formulario-personalizado/{id}")
    Call<RespostaFormularioPersonalizado> getRespostaFormularioPersonalizadoById(@Path("id") String id);
    @POST("https://ms-sinara-mongo.onrender.com/resposta-formulario-personalizado/inserir")
    boolean insertRespostaFormularioPersonalizado(List<Respostas> respostas, Date data, String idForm, String idOperario);
}
