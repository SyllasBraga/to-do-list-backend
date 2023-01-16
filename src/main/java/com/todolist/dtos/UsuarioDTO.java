package com.todolist.dtos;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String login;
    private String senha;
    private List<TarefaDTO> tarefas = new ArrayList<>();

    public UsuarioDTO(Usuario usuario){
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();
    }

    public UsuarioDTO(Usuario usuario, List<Tarefa> tarefas){
        this(usuario);
        tarefas.forEach(x -> this.tarefas.add(new TarefaDTO(x, x.getUsuario())));
    }
}
