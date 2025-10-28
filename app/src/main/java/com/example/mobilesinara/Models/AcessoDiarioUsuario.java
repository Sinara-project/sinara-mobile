package com.example.mobilesinara.Models;

import java.util.Date;

public class AcessoDiarioUsuario {
    private int id;
    private Date data;
    private String atividades;
    private int idOperario;
    private int idEmpresa;
    public AcessoDiarioUsuario(){}
    public AcessoDiarioUsuario(int id, Date data, String atividades, int idOperario, int idEmpresa){
        this.id = id;
        this.data = data;
        this.atividades = atividades;
        this.idOperario = idOperario;
        this.idEmpresa = idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public Date getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public int getIdOperario() {
        return idOperario;
    }

    public String getAtividades() {
        return atividades;
    }

    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdOperario(int idOperario) {
        this.idOperario = idOperario;
    }
}
