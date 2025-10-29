package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.campos;
import com.example.mobilesinara.Models.FormularioPersonalizado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFormularioPersonalizado {
    @GET("https://ms-sinara-mongo.onrender.com/formulario-personalizado/{id}")
    Call<FormularioPersonalizado> getFormularioPersonalizado(@Path("id") String id);
    @GET("https://ms-sinara-mongo.onrender.com/formulario-personalizado/buscar-por-empresa/{idEmpresa}")
    Call<List<FormularioPersonalizado>> getFormularioPersonalizadoPorEmpresa(@Path("idEmpresa") int idEmpresa);

    @FormUrlEncoded
    @POST("https://ms-sinara-mongo.onrender.com/formulario-personalizado/inserir")
    Call<FormularioPersonalizado> InsertFormularioPersonalizado(
            @Field("idCriador") int idCriador,
            @Field("titulo") String titulo,
            @Field("descricao") String descricao,
            @Field("campos") List<campos> campos,
            @Field("permissoes") List<String> permissoes
    );
}
