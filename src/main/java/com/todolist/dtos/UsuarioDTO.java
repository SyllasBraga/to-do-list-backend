package com.todolist.dtos;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.service.validation.UsuarioCreateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "Insira um nome!")
    @Size(min = 3, max = 255, message = "O nome deve ter de 3 até 255 caracteres!")
    private String nome;
    @Email(message = "Insira um email válido!")
    private String login;
    @Size(min = 8, max = 255, message = "Insira uma senha que tenha entre 8 até 255 caracteres!")
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
