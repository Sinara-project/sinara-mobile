package com.example.mobilesinara.Models;

import java.util.Date;
import java.util.List;

public class RespostaFormularioPersonalizado {
    private String id;
    private List<Respostas> respostas;
    private Date data;
    private String idForm;
    private String idOperario;

    public RespostaFormularioPersonalizado(){}
    public RespostaFormularioPersonalizado(String id, List<Respostas> respostas, Date data, String idForm, String idOperario){
        this.id = id;
        this.respostas = respostas;
        this.data = data;
        this.idForm = idForm;
        this.idOperario = idOperario;
    }

    public String getId() {
        return id;
    }

    public List<Respostas> getRespostas() {
        return respostas;
    }

    public Date getData() {
        return data;
    }

    public String getIdForm() {
        return idForm;
    }

    public String getIdOperario() {
        return idOperario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRespostas(List<Respostas> respostas) {
        this.respostas = respostas;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    public void setIdOperario(String idOperario) {
        this.idOperario = idOperario;
    }
}
