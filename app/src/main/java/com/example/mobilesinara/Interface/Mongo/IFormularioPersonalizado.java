package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.Campos;
import com.example.mobilesinara.Models.FormularioPersonalizado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFormularioPersonalizado {
    @GET("https://ms-sinara-mongo.onrender.com/formulario-personalizado/{id}")
    Call<FormularioPersonalizado> getFormularioPersonalizado(@Path("id") String id);
    @GET("https://ms-sinara-mongo.onrender.com/formulario-personalizado/buscar-por-empresa/{idEmpresa}")
    Call<FormularioPersonalizado> getFormularioPersonalizadoPorEmpresa(@Path("idEmpresa") String idEmpresa);
    @POST("https://ms-sinara-mongo.onrender.com/formulario-personalizado/inserir")
    boolean InsertFormularioPersonalizado(@Body String idCriador, String titulo, String descricao, List<Campos> campos, List<String> idPermissao);

}
