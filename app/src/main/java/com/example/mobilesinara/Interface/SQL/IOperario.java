package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.Models.PerfilOperario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IOperario {
    @GET("/user/operario/buscarPorId/{id}")
    Call<Operario> getOperarioPorId(@Path("id") int id);
    @GET("/user/operario/perfilOperario/{id}")
    Call<PerfilOperario> getPerfilOperarioPorId(@Path("id") int id);
    @GET("/user/operario/verificarReconhecimento/{id}")
    Call<Boolean> verificarReconhecimento(@Path("id") int id);
    @POST("/user/operario/atualizar-status")
    Call<String> atualizarStatus(@Body int idOperario, boolean ativo, boolean ferias);
    @POST("/user/operario/uploadReconhecimento/{id}")
    Call<String> uploadReconhecimento(@Path("id") int id, @Body String file);
    @PUT("/user/operario/atualizar/{id}")
    Call<String> uploadReconhecimento(@Path("id") int id, @Body Operario operario);
}
