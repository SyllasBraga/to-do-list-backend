package com.todolist.service;

import com.todolist.entities.Tarefa;
import com.todolist.repository.TarefaRepository;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public String create(Tarefa tarefa){
        tarefaRepository.save(tarefa);
        return "Criado com sucesso!";
    }

    public String update(Long id, Tarefa tarefa){
        return tarefaRepository.findById(id).map(Record ->{
            Record.setDataCadastro(tarefa.getDataCadastro());
            Record.setStatusTarefa(tarefa.getStatusTarefa());
            Record.setDescricao(tarefa.getDescricao());
            Record.setDataPrazo(tarefa.getDataPrazo());
            Record.setDataTermino(tarefa.getDataTermino());
            tarefaRepository.save(Record);
            return "Atualizado com sucesso!";
        }).orElse("Erro na atualização!");
    }

    public String delete(Long id){
        tarefaRepository.deleteById(id);
        return "Sucesso na deleção";
    }
}
