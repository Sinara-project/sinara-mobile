package com.example.mobilesinara.Interface.SQL;

import com.example.mobilesinara.Models.Planos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPlanos {
    @GET("/api/admin/planos/listar")
    Call<List<Planos>> getPlanos();
    @GET("/api/admin/planos/buscarPorId/{id}")
    Call<Planos> getPlanosPorId(@Path("id") int id);
}
