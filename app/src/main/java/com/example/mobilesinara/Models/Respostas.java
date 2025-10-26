package com.example.mobilesinara.Models;

import java.lang.annotation.Repeatable;

public class Respostas {
    private String campoLabel;
    private String campoTipo;
    private double valor;
    public Respostas(){}
    public Respostas(String campoLabel, String campoTipo, double valor){
        this.campoLabel = campoLabel;
        this.campoTipo = campoTipo;
        this.valor = valor;
    }
}
