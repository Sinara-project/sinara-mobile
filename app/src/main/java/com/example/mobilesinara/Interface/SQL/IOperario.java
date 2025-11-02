package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.Operario;
import com.example.mobilesinara.Models.OperarioLoginRequestDTO;
import com.example.mobilesinara.Models.PerfilOperario;
import com.example.mobilesinara.Models.SenhaRequest;
import com.example.mobilesinara.Models.SenhaRequestDTO;

import java.util.Map;

import kotlin.jvm.JvmSuppressWildcards;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IOperario {
    @GET("/api/user/operario/buscarPorId/{id}")
    Call<Operario> getOperarioPorId(@Path("id") int id);
    @GET("/api/user/operario/perfilOperario/{id}")
    Call<PerfilOperario> getPerfilOperarioPorId(@Path("id") int id);
    @GET("/api/user/operario/verificarReconhecimento/{id}")
    Call<Boolean> verificarReconhecimento(@Path("id") int id);
    @GET("/api/user/operario/obterId/{cpf}")
    Call<Integer> getIdPorCpf(@Path("cpf") String cpf);
    @POST("/api/user/operario/atualizar-status")
    Call<String> atualizarStatus(@Body int idOperario, boolean ativo, boolean ferias);
    @POST("/api/user/operario/uploadReconhecimento/{id}")
    Call<String> uploadReconhecimento(@Path("id") int id, @Body String file);
    @PUT("/api/user/operario/atualizar/{id}")
    Call<String> uploadReconhecimento(@Path("id") int id, @Body Operario operario);
    @POST("/api/user/operario/loginOperario")
    Call<Boolean> loginOperario(@Body OperarioLoginRequestDTO loginRequest);
    @PATCH("/api/user/operario/atualizarSenha/{id}")
    Call<Operario> atualizarSenha(@Path("id") int id, @Body SenhaRequestDTO request);
    @POST("/api/user/operario/verificarSenha")
    Call<Boolean> verificarSenha(@Query("idOperario") int idOperario, @Query("senha") String senha);
    @Multipart
    @POST("/api/user/operario/uploadReconhecimento/{id}")
    Call<String> uploadFotoReconhecimento(@Path("id") Integer id, @Part MultipartBody.Part file);
    @Multipart
    @POST("/api/user/operario/verificarFacial")
    Call<Boolean> verificarReconhecimento(@PartMap Map<String, RequestBody> params,
                                          @Part MultipartBody.Part fotoTeste
    );
}
