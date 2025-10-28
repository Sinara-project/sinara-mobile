package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.CartaoCredito;
import com.example.mobilesinara.Models.Operario;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICartaoCredito {
    @GET("/user/CartaoCredito/buscarPorId/{id}")
    Call<CartaoCredito> getCartaoCreditoPorId(@Path("id") int id);
    @GET("/user/CartaoCredito/listar")
    Call<List<CartaoCredito>> getCartaoCredito();
    @GET("/user/CartaoCredito/validarCartaoCredito")
    Call<Boolean> validarCartaoCredito(@Body String numero, Date validade);
    @PATCH("/user/CartaoCredito/atualizar/{id}")
    Call<String> atualizarCartaoCredito(@Path("id") int id, @Body CartaoCredito cartaoCredito);
    @POST("/user/CartaoCredito/inserir")
    Call<String> inserirCartaoCredito(@Body CartaoCredito cartaoCredito);
    @DELETE("/user/CartaoCredito/excluir/{id}")
    Call<String> deletarCartaoCredito(@Path("id") int id);
}
