package com.example.mobilesinara.Models;

public class Respostas {
    private String id;
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
