package com.example.mobilesinara.Models;

public class PerfilOperario {
    private String imagemUrl;
    private int horasPrevistas;
    private String nome;
    public PerfilOperario(){}
    public PerfilOperario(String imagemUrl, int horasPrevistas, String nome){
        this.imagemUrl = imagemUrl;
        this.horasPrevistas = horasPrevistas;
        this.nome = nome;
    }

    public void setHorasPrevistas(int horasPrevistas) {
        this.horasPrevistas = horasPrevistas;
    }

    public void setimagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getimagemUrl() {
        return imagemUrl;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHorasPrevistas() {
        return horasPrevistas;
    }

    public String getNome() {
        return nome;
    }
}
