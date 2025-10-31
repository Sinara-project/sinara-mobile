package com.example.mobilesinara.Models;

import java.util.Date;

public class FormularioItem {
    private String tipo;
    private String titulo;
    private String status;
    private Date data;

    public FormularioItem(String tipo, String titulo, String status, Date data) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.status = status;
        this.data = data;
    }

    public String getTipo() { return tipo; }
    public String getTitulo() { return titulo; }
    public String getStatus() { return status; }
    public Date getData() { return data; }
}
