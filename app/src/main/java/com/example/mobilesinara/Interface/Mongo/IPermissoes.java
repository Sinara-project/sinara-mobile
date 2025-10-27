package com.example.mobilesinara.Interface.Mongo;

import com.example.mobilesinara.Models.Permissao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPermissoes {
    @GET("https://ms-sinara-mongo.onrender.com/permissoes/{id}")
    Call<Permissao> getPermissaoPorId(@Path("id") String id);
    @POST("https://ms-sinara-mongo.onrender.com/permissoes/inserir")
    boolean insertPermissao(String idEmpresa, String nomePermissao, List<String> idFuncionario);

    @GET("https://ms-sinara-mongo.onrender.com//permissoes/buscar-por-empresa/{idEmpresa}")
    Call<List<Permissao>> getPermissaoPorEmpresa(@Path("idEmpresa") String id);
}
