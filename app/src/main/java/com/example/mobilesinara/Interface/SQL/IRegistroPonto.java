package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.DTO.HorasTrabalhadasResponse;
import com.example.mobilesinara.Models.FormularioPersonalizado;
import com.example.mobilesinara.Models.RegistroPontoModel;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRegistroPonto {

    @GET("/api/user/registroPonto/buscarPorId/{id}")
    Call<RegistroPontoModel> getRegistroPontoPorId(@Path("id") int id);
    @GET("/api/user/registroPonto/horarioEntradaSaida/{id}")
    Call<RegistroPontoModel> getHorarioEntradaSaida(@Path("id") int id);
    @GET("/api/user/registroPonto/horasTrabalhadas/{idOperario}")
    Call<HorasTrabalhadasResponse> getHorasTrabalhadas(@Path("idOperario") int idOperario);
    @GET("/api/user/registroPonto/listarStatusOperario/{idOperario}")
    Call<Boolean> getStatusOperario(@Path("idOperario") int idOperario);
    @GET("/api/user/registroPonto/quantidadeRegistroPonto/{idOperario}")
    Call<Integer> getQuantidadeRegistroPonto(@Path("idOperario") int idOperario);
    @GET("/api/user/registroPonto/ultimoTurno/{idOperario}")
    Call<Date> getUltimoTurno(@Path("idOperario") int idOperario);
    @POST("/api/user/registroPonto/inserir")
    Call<String> inserirRegistroPonto(@Body Date horarioEntrada, Date horarioSaida, int idOperario, int idEmpresa);
}
