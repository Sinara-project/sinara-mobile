package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.Empresa;
import com.example.mobilesinara.Models.EmpresaLoginResponseDTO;
import com.example.mobilesinara.Models.PerfilEmpresa;
import com.example.mobilesinara.Models.EmpresaLoginRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IEmpresa {
    @GET("/api/admin/empresa/buscarPorId/{id}")
    Call<Empresa> getEmpresaPorId(@Path("id") int id);
    @GET("/api/admin/empresa/obterEmpresaPorCnpj/{cnpj}")
    Call<Empresa> getEmpresaPorCnpj(@Path("cnpj") String cnpj);
    @GET("/api/admin/empresa/listarPerfilEmpresa/{id}")
    Call<PerfilEmpresa> listarPerfilEmpresa(@Path("id") int id);
    @PATCH("/api/admin/empresa/atualizarSenhaAreaRestrita/{id}")
    Call<String> atualizarSenhaAreaRestrita(@Path("id") int id, @Body String novaSenha);
    @POST("/api/admin/empresa/mudar-plano/")
    Call<String> mudarPlano(@Body int idEmpresa, int idCartao);
    @POST("/api/admin/empresa/rebaixarPlanos/")
    Call<String> rebaixarPlanos();
    @POST("/api/admin/empresa/loginEmpresa")
    Call<EmpresaLoginResponseDTO> loginEmpresa(@Body EmpresaLoginRequestDTO loginRequest);
    @POST("/api/admin/empresa/validarCodigo")
    Call<Boolean> validarCodigo(@Body String cnpj, String senha);
}
