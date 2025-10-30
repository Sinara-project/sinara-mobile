package com.example.mobilesinara;

import com.example.mobilesinara.Models.Permissao;

import java.util.Date;

public class FormularioAcao extends Permissao {
    private int id;

    private String titulo;

    private Date dataPreenchimento;

    private String status;

    public FormularioAcao(String titulo, Date dataPreenchimento, String status){
        this.titulo = titulo;
        this.dataPreenchimento = dataPreenchimento;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public Date getDataPreenchimento() {
        return dataPreenchimento;
    }
    public String getStatus(){
        return status;
    }
}
