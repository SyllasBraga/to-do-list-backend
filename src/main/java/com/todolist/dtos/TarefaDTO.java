package com.todolist.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.enums.TarefaStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class TarefaDTO {

    private Long id;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TarefaStatus statusTarefa;
    private Date dataCadastro;
    private Date dataPrazo;
    private Date dataTermino;
    @JsonIgnore
    private UsuarioDTO usuario;

    public TarefaDTO(Tarefa tarefa, Usuario usuario) {
        this.id = tarefa.getId();
        this.descricao = tarefa.getDescricao();
        this.statusTarefa = tarefa.getStatusTarefa();
        this.dataCadastro = tarefa.getDataCadastro();
        this.dataPrazo = tarefa.getDataPrazo();
        this.dataTermino = tarefa.getDataTermino();
        this.usuario = new UsuarioDTO(usuario);
    }
}
