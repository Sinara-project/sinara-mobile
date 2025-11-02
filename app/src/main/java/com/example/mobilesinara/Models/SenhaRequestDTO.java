package com.example.mobilesinara.Models;

public class SenhaRequestDTO {
    private String novaSenha;

    public SenhaRequestDTO(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
