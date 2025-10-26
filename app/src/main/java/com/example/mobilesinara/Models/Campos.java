package com.example.mobilesinara.Models;

import java.util.List;

public class Campos {
    private String label;
    private String tipo;
    private Boolean Obrigatorio;
    private List<String> opcoes;

    public Campos(){}
    public Campos(String label, String tipo, Boolean obrigatorio, List<String> opcoes){
        this.label = label;
        this.tipo = tipo;
        this.Obrigatorio = obrigatorio;
        this.opcoes = opcoes;
    }

    public String getLabel() {
        return label;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getObrigatorio() {
        return Obrigatorio;
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
        Obrigatorio = obrigatorio;
    }

    public void setOpcoes(List<String> opcoes) {
        this.opcoes = opcoes;
    }
}
