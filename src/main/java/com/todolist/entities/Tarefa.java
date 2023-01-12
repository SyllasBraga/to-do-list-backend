package com.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.todolist.enums.TarefaStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TarefaStatus statusTarefa;
    private Date dataCadastro;
    private Date dataPrazo;
    private Date dataTermino;

    @ManyToOne
    @JoinColumn(name = "usuario")
    @JsonIgnore
    private Usuario usuario;
}
