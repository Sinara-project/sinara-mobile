package com.example.mobilesinara.Models;

public class EmpresaLoginResponseDTO {
    private Long id;
    private String cnpj;
    private String nome;
    private String codigo;
    private String email;
    private String ramoAtuacao;
    private String imagemUrl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRamoAtuacao() { return ramoAtuacao; }
    public void setRamoAtuacao(String ramoAtuacao) { this.ramoAtuacao = ramoAtuacao; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
}