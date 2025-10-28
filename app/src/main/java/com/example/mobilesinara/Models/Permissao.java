package com.example.mobilesinara.Models;

import java.util.List;

public class Permissao {
    private String id;
    private int idEmpresa;
    private String nomePermissao;
    private List<String> idFuncionario;
    public Permissao(){}
    public Permissao(String id, int idEmpresa, String nomePermissao, List<String> idFuncionario){
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.nomePermissao = nomePermissao;
        this.idFuncionario = idFuncionario;
    }

    public void setIdFuncionario(List<String> idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNomePermissao() {
        return nomePermissao;
    }

    public List<String> getIdFuncionario() {
        return idFuncionario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNomePermissao(String nomePermissao) {
        this.nomePermissao = nomePermissao;
    }
}
