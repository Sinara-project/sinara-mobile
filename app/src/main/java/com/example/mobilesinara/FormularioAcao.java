package com.example.mobilesinara;

import com.example.mobilesinara.cadastro.operario.Permissao;

import java.time.LocalDateTime;
import java.util.Date;

public class FormularioAcao extends Permissao {
    private int id;

    private String titulo;

    private LocalDateTime dataPreenchimento;

    private String status;

    public FormularioAcao(String titulo, LocalDateTime dataPreenchimento, String status){
        this.titulo = titulo;
        this.dataPreenchimento = dataPreenchimento;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getDataPreenchimento() {
        return dataPreenchimento;
    }
    public String getStatus(){
        return status;
    }
}
