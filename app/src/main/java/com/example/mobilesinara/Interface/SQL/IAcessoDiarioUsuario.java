package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.AcessoDiarioUsuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAcessoDiarioUsuario {
    @POST("/acessoDiarioUsuarios/registrar")
    Call<String> registrar(@Body AcessoDiarioUsuario acessoDiarioUsuario);
}
