package com.example.mobilesinara.Models;

public class RegistroPontoRequest {
    private String horarioEntrada;
    private String horarioSaida;
    private Integer idOperario;
    private Integer idEmpresa;

    public RegistroPontoRequest(String horarioEntrada, String horarioSaida, Integer idOperario, Integer idEmpresa) {
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.idOperario = idOperario;
        this.idEmpresa = idEmpresa;
    }

    public String getHorarioEntrada() { return horarioEntrada; }
    public void setHorarioEntrada(String horarioEntrada) { this.horarioEntrada = horarioEntrada; }
    public String getHorarioSaida() { return horarioSaida; }
    public void setHorarioSaida(String horarioSaida) { this.horarioSaida = horarioSaida; }
    public Integer getIdOperario() { return idOperario; }
    public void setIdOperario(Integer idOperario) { this.idOperario = idOperario; }
    public Integer getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(Integer idEmpresa) { this.idEmpresa = idEmpresa; }
}
