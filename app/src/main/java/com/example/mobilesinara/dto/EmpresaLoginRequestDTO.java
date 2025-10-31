package com.example.mobilesinara.dto;

public class EmpresaLoginRequestDTO {
    private String cnpj;
    private String senha;

    public EmpresaLoginRequestDTO(String cnpj, String senha) {
        this.cnpj = cnpj;
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
