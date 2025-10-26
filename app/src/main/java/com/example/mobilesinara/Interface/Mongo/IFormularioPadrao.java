package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.FormularioPadrao;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFormularioPadrao {
    @GET("https://ms-sinara-mongo.onrender.com/formulario-padrao/{id}")
    Call<FormularioPadrao> getFormularioPadrao(@Path("id") String id);
    @GET("https://ms-sinara-mongo.onrender.com/formulario-padrao/buscar-por-empresa/{idEmpresa}")
    Call<List<FormularioPadrao>> getFormularioPadraoPorEmpresa(@Path("idEmpresa") String idEmpresa);
    @POST("https://ms-sinara-mongo.onrender.com/formulario-padrao/inserir")
    boolean insertFormularioPadrao(String idEmpresa, Date dataPreenchimento, double cloroResidual, double corAguaBruta, double corAguaTratada, double fluoreto, double nitrato, double phAguaBruta, double phAguaTratada, double turbinezAguaBruta, double turbidezAguaTratada, String qualidade, String idFuncionario);
}
