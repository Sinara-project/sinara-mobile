package com.example.mobilesinara.api;

import com.example.mobilesinara.Models.OperarioLoginRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OperarioService {

    @POST("/api/user/operario/loginOperario")
    Call<Boolean> loginOperario(@Body OperarioLoginRequestDTO loginRequest);
}
