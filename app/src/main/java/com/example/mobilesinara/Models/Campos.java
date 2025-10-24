package com.example.mobilesinara.Models;

public class Campos {
    private String label;
    private String tipo;
    private Boolean Obrigatorio;

    public Campos(){}
    public Campos(String label, String tipo, Boolean obrigatorio){
        this.label = label;
        this.tipo = tipo;
        this.Obrigatorio = obrigatorio;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        Obrigatorio = obrigatorio;
    }
}
