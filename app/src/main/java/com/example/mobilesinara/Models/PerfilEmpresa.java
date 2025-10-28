package com.example.mobilesinara.Models;

public class PerfilEmpresa {
    private String email;
    private String ramoatuacao;
    private String codigo;
    private String nome;
    private int id;
    private String cnpj;
    private String telefone;
    private String imagemurl;
    public PerfilEmpresa(){}
    public PerfilEmpresa(String email, String ramoatuacao, String codigo,
                         String nome, int id, String cnpj, String telefone, String imagemurl){
        this.email = email;
        this.ramoatuacao = ramoatuacao;
        this.codigo = codigo;
        this.nome = nome;
        this.id = id;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.imagemurl = imagemurl;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getImagemurl() {
        return imagemurl;
    }

    public String getRamoatuacao() {
        return ramoatuacao;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setImagemurl(String imagemurl) {
        this.imagemurl = imagemurl;
    }

    public void setRamoatuacao(String ramoatuacao) {
        this.ramoatuacao = ramoatuacao;
    }
}
