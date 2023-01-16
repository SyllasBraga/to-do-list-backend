package com.todolist.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.enums.TarefaStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class TarefaDTO {

    private Long id;
    @NotBlank(message = "Insira uma breve descrição!")
    @Size(min = 4, max = 255, message = "Precisa ter de 4 até 255 caracteres!")
    private String descricao;
    @NotNull(message = "Insira um status compatível, sendo eles: CONCLUIDA, VENCIDA e PENDENTE")
    @Enumerated(EnumType.STRING)
    private TarefaStatus statusTarefa;
    @NotNull(message = "Insira uma data de cadastro!")
    private Date dataCadastro;
    @NotNull(message = "Insira uma data referente ao prazo!")
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
