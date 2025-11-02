package com.example.mobilesinara.Models;

public class SenhaRequest {
    private Integer id;
    private String senha;

    public SenhaRequest(Integer id, String senha) {
        this.id = id;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }
}