package com.example.mobilesinara.Models;

public class Planos {
    private String nome;
    private double precoMensal;
    private double precoAnual;
    private String recursos;
    public Planos(){}
    public Planos(String nome, double precoMensal, double precoAnual, String recursos){
        this.nome = nome;
        this.precoMensal = precoMensal;
        this.precoAnual = precoAnual;
        this.recursos = recursos;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoAnual() {
        return precoAnual;
    }

    public double getPrecoMensal() {
        return precoMensal;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setPrecoAnual(double precoAnual) {
        this.precoAnual = precoAnual;
    }

    public void setPrecoMensal(double precoMensal) {
        this.precoMensal = precoMensal;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }
}
