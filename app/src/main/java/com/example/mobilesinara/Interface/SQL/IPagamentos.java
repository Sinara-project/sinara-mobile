package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.Pagamento;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPagamentos {
    @GET("/api/admin/pagamento/buscarPorId/{id}")
    Call<Pagamento> getPagamentoPorId(@Path("id") int id);

}
