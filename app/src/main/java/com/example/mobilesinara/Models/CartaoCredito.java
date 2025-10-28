package com.example.mobilesinara.Models;

import java.util.Date;
import java.util.Locale;

public class CartaoCredito {
    private String numero;
    private String nomeTitular;
    private Date validade;
    private String cvv;
    private int idEmpresa;
    public CartaoCredito(){}
    public CartaoCredito(String numero, String nomeTitular, Date validade, String cvv, int idEmpresa){
        this.numero = numero;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.cvv = cvv;
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Date getValidade() {
        return validade;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getCvv() {
        return cvv;
    }

    public String getNumero() {
        return numero;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }
}
