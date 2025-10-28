package com.example.mobilesinara.Models;

import java.util.Date;

public class RegistroPontoModel {
    private Date horarioEntrada;
    private Date horarioSaida;
    private int idOperario;
    private int idEmpresa;
    public RegistroPontoModel(){}
    public RegistroPontoModel(Date horarioEntrada, Date horarioSaida, int idOperario, int idEmpresa){
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.idOperario = idOperario;
        this.idEmpresa = idEmpresa;
    }
}
