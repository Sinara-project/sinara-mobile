package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.Campos;
import com.example.mobilesinara.Models.FormularioPersonalizado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFormularioPersonalizado {
    @GET("https://ms-sinara-mongo.onrender.com/formulario-personalizado/{id}")
    Call<FormularioPersonalizado> getFormularioPersonalizado(@Path("id") String id);
    @GET("https://ms-sinara-mongo.onrender.com/formulario-personalizado/buscar-por-empresa/{idEmpresa}")
    Call<FormularioPersonalizado> getFormularioPersonalizadoPorEmpresa(@Path("idEmpresa") String idEmpresa);

    @FormUrlEncoded
    @POST("formulario-personalizado/inserir")
    Call<FormularioPersonalizado> InsertFormularioPersonalizado(
            @Field("idCriador") String idCriador,
            @Field("titulo") String titulo,
            @Field("descricao") String descricao,
            @Field("campos") List<Campos> campos,
            @Field("permissoes") List<String> permissoes
    );
}
