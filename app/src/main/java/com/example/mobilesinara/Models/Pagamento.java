package com.example.mobilesinara.Models;

import java.util.Date;

public class Pagamento {
    private double valor;
    private Date dataPagamento;
    private String status;
    private int idCartaoCredito;
    private int idEmpresa;
    public Pagamento(){}
    public Pagamento(double valor, Date dataPagamento, String status, int idCartaoCredito, int idEmpresa){
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.idCartaoCredito = idCartaoCredito;
        this.idEmpresa = idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getIdCartaoCredito() {
        return idCartaoCredito;
    }

    public String getStatus() {
        return status;
    }

    public void setIdCartaoCredito(int idCartaoCredito) {
        this.idCartaoCredito = idCartaoCredito;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
