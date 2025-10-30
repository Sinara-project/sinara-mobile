package com.example.mobilesinara.Models;

public class PerfilOperario {
    private String imageUrl;
    private int horasPrevistas;
    private String nome;
    public PerfilOperario(){}
    public PerfilOperario(String imageUrl, int horasPrevistas, String nome){
        this.imageUrl = imageUrl;
        this.horasPrevistas = horasPrevistas;
        this.nome = nome;
    }

    public void setHorasPrevistas(int horasPrevistas) {
        this.horasPrevistas = horasPrevistas;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
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
