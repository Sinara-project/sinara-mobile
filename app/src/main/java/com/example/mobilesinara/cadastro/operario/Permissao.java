package com.example.mobilesinara.cadastro.operario;

public class Permissao {
    private String nome;
    private boolean selecionado;
    public Permissao(){}
    public Permissao(String nome, boolean selecionado){
        this.nome = nome;
        this.selecionado = selecionado;
    }

    public String getNome() {
        return nome;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
