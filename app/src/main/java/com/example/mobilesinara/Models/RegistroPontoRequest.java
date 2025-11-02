package com.example.mobilesinara.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class RegistroPontoRequest {
    @SerializedName("horarioEntrada")
    @Expose(serialize = true, deserialize = true)
    private LocalDateTime horarioEntrada;

    @SerializedName("horarioSaida")
    @Expose(serialize = true, deserialize = true)
    private LocalDateTime horarioSaida;
    @SerializedName("idOperario")
    private int idOperario;
    @SerializedName("idEmpresa")
    private int idEmpresa;

    public RegistroPontoRequest(LocalDateTime entrada, LocalDateTime saida, int idOperario, int idEmpresa) {
        this.horarioEntrada = entrada;
        this.horarioSaida = saida;
        this.idOperario = idOperario;
        this.idEmpresa = idEmpresa;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public int getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(int idOperario) {
        this.idOperario = idOperario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
