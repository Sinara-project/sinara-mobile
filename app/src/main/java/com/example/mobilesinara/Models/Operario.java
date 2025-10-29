package com.example.mobilesinara.Models;

public class Operario {
    private int idEmpresa;
    private String imageUrl;
    private String cpf;
    private String nome;
    private String email;
    private String cargo;
    private String setor;
    private boolean ferias;
    private boolean ativo;
    private String senha;
    private int horasPrevistas;
    public Operario(){}
    public Operario(int idEmpresa, String imageUrl, String cpf, String nome, String email, String cargo, String setor, boolean ferias, boolean ativo, String senha, int horasPrevistas){
        this.idEmpresa = idEmpresa;
        this.imageUrl = imageUrl;
         this.cpf = cpf;
         this.nome = nome;
         this.email = email;
         this.cargo = cargo;
         this.setor = setor;
         this.ferias = ferias;
         this.ativo = ativo;
         this.senha = senha;
         this.horasPrevistas = horasPrevistas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isFerias() {
        return ferias;
    }

    public int getHorasPrevistas() {
        return horasPrevistas;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getSetor() {
        return setor;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFerias(boolean ferias) {
        this.ferias = ferias;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setHorasPrevistas(int horasPrevistas) {
        this.horasPrevistas = horasPrevistas;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
