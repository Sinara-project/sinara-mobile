package com.example.mobilesinara.Models;

public class Empresa {
    private String cnpj;
    private String nome;
    private String senha;
    private String senhaAreaRestrita;
    private String codigo;
    private String imageUrl;
    private String email;
    private String ramoAtuacao;
    private String telefone;
    private int idPlano;
    public Empresa(){}
    public Empresa(String cnpj, String nome, String senha, String senhaAreaRestrita, String email,String codigo, String imageUrl, String ramoAtuacao, String telefone, int idPlano){
        this.cnpj = cnpj;
        this.nome = nome;
        this.senha = senha;
        this.senhaAreaRestrita = senhaAreaRestrita;
        this.codigo = codigo;
        this.imageUrl = imageUrl;
        this.email = email;
        this.ramoAtuacao = ramoAtuacao;
        this.telefone = telefone;
        this.idPlano = idPlano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRamoAtuacao() {
        return ramoAtuacao;
    }

    public String getSenhaAreaRestrita() {
        return senhaAreaRestrita;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public void setRamoAtuacao(String ramoAtuacao) {
        this.ramoAtuacao = ramoAtuacao;
    }

    public void setSenhaAreaRestrita(String senhaAreaRestrita) {
        this.senhaAreaRestrita = senhaAreaRestrita;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
