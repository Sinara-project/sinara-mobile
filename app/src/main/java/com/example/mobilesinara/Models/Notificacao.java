package com.example.mobilesinara.Models;

import java.util.Date;

public class Notificacao {
    private String id;

    private Date data;

    private String mensagem;

    private String tipo;

    public Notificacao(String id, Date data, String mensagem, String tipo) {
        this.id = id;
        this.data = data;
        this.mensagem = mensagem;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
