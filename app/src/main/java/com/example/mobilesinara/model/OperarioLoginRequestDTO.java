package com.example.mobilesinara.model;

public class OperarioLoginRequestDTO {
    private String email;
    private String cpf;
    private String senha;
    private String codigoEmpresa;

    public OperarioLoginRequestDTO(String email, String cpf, String senha, String codigoEmpresa) {
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}
