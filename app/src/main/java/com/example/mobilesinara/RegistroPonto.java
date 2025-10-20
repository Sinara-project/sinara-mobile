package com.example.mobilesinara;

import java.util.Date;

public class RegistroPonto {
    private int id;

    private String idFuncionario;

    private String idEmpresa;

    private Date horario;

    public RegistroPonto(){}
    public RegistroPonto(int id, String idFuncionario, String idEmpresa, Date horario){
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.idEmpresa = idEmpresa;
        this.horario = horario;
    }
}
