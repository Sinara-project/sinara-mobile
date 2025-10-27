package com.example.mobilesinara.Models;

import java.util.List;

public class FormularioPersonalizado {
    private String id;
    private String idCriador;
    private String titulo;
    private String descricao;
    private List<campos> campos;
    private List<String> idPermissao;
    public FormularioPersonalizado(){}
    public FormularioPersonalizado(String idCriador, String titulo, String descricao, List<campos> campos, List<String> idPermissao){
        this.idCriador = idCriador;
        this.titulo = titulo;
        this.descricao = descricao;
        this.campos = campos;
        this.idPermissao = idPermissao;
    }

    public String getIdCriador() {
        return idCriador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<campos> getCampos() {
        return campos;
    }

    public List<String> getIdPermissao() {
        return idPermissao;
    }

    public void setIdCriador(String idCriador) {
        this.idCriador = idCriador;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCampos(List<campos> campos) {
        this.campos = campos;
    }

    public void setIdPermissao(List<String> idPermissao) {
        this.idPermissao = idPermissao;
    }
}
