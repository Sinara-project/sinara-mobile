package com.example.mobilesinara.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class campos {
    private String id;
    @SerializedName("label")
    private String label;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("obrigatorio")
    private Boolean obrigatorio;
    @SerializedName("opcoes")
    private List<String> opcoes;

    public campos(){}
    public campos(String label, String tipo, Boolean obrigatorio, List<String> opcoes){
        this.label = label;
        this.tipo = tipo;
        this.obrigatorio = obrigatorio;
        this.opcoes = opcoes;
    }

    public String getLabel() {
        return label;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getObrigatorio() {
        return obrigatorio;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public void setOpcoes(List<String> opcoes) {
        this.opcoes = opcoes;
    }
}
