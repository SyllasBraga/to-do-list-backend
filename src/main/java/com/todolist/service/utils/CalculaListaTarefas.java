package com.todolist.service.utils;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculaListaTarefas {

    TarefaRepository tarefaRepository;

    public CalculaListaTarefas(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> recuperaListaTarefas(Usuario usuario){
        return tarefaRepository.listaPorUsuario(usuario.getId());
    }
}
