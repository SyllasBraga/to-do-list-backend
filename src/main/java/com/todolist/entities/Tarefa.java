package com.todolist.entities;

import com.todolist.enums.TarefaStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private TarefaStatus statusTarefa;
    private Date dataCadastro;
    private Date dataPrazo;
    private Date dataTermino;

    @OneToOne
    @JoinColumn(name = "id")
    private Usuario usuario;
}
