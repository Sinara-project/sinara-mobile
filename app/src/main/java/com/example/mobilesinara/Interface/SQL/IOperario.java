package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.Models.OperarioLoginRequestDTO;
import com.example.mobilesinara.Models.PerfilOperario;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IOperario {
    @GET("/api/user/operario/buscarPorId/{id}")
    Call<Operario> getOperarioPorId(@Path("id") int id);
    @GET("/api/user/operario/perfilOperario/{id}")
    Call<PerfilOperario> getPerfilOperarioPorId(@Path("id") int id);
    @GET("/api/user/operario/verificarReconhecimento/{id}")
    Call<Boolean> verificarReconhecimento(@Path("id") int id);
    @POST("/api/user/operario/atualizar-status")
    Call<String> atualizarStatus(@Body int idOperario, boolean ativo, boolean ferias);
    @POST("/api/user/operario/uploadReconhecimento/{id}")
    Call<String> uploadReconhecimento(@Path("id") int id, @Body String file);
    @PUT("/api/user/operario/atualizar/{id}")
    Call<String> uploadReconhecimento(@Path("id") int id, @Body Operario operario);
    @POST("/api/user/operario/loginOperario")
    Call<Boolean> loginOperario(@Body OperarioLoginRequestDTO loginRequest);
    @Multipart
    @POST("/api/user/operario/uploadReconhecimento/{id}")
    Call<String> uploadFotoReconhecimento(@Path("id") Integer id, @Part MultipartBody.Part file);
    @Multipart
    @POST("/api/user/operario/verificarReconhecimento/{id}")
    Call<Boolean> verificarReconhecimento(@Path("id") Integer id, @Part MultipartBody.Part file);
}
