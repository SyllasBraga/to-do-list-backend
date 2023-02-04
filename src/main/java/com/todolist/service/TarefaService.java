package com.todolist.service;

import com.todolist.dtos.TarefaCreateDTO;
import com.todolist.dtos.TarefaDTO;
import com.todolist.dtos.UsuarioDTO;
import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.enums.TarefaStatus;
import com.todolist.exceptions.ResourceNotFoundException;
import com.todolist.exceptions.CodStatusException;
import com.todolist.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public TarefaDTO getById(Long id){
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada!"));
        TarefaDTO tarefaDto = new TarefaDTO(tarefa, tarefa.getUsuario());
        return tarefaDto;
    }

    public TarefaDTO create(TarefaDTO tarefaDto){
        Tarefa tarefa = new Tarefa();
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(tarefaDto, tarefa);
        BeanUtils.copyProperties(tarefaDto.getUsuario(), usuario);
        tarefa.setUsuario(usuario);
        tarefaRepository.save(tarefa);

        return tarefaDto;
    }

    public TarefaDTO update(Long id, TarefaDTO tarefaDto){

        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaDto, tarefa);

        return tarefaRepository.findById(id).map(Record ->{
            Record.setDataCadastro(tarefa.getDataCadastro());
            Record.setStatusTarefa(tarefa.getStatusTarefa());
            Record.setDescricao(tarefa.getDescricao());
            Record.setDataPrazo(tarefa.getDataPrazo());
            Record.setDataTermino(tarefa.getDataTermino());
            tarefaRepository.save(Record);

            return tarefaDto;
        }).orElseThrow(()-> new ResourceNotFoundException("Tarefa não encontrada!"));
    }

    public String delete(Long id){
        tarefaRepository.deleteById(id);

        return "Tarefa deletada com sucesso.";
    }

    public TarefaDTO atualizaStatus(Long idTarefa, int codStatus){

        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada!"));

        switch (codStatus){
            case 1:
                tarefa.setStatusTarefa(TarefaStatus.CONCLUIDA);
                tarefaRepository.save(tarefa);

                return new TarefaDTO(tarefa);
            case 2:
                tarefa.setStatusTarefa(TarefaStatus.PENDENTE);
                tarefaRepository.save(tarefa);

                return new TarefaDTO(tarefa);
            case 3:
                tarefa.setStatusTarefa(TarefaStatus.VENCIDA);
                tarefaRepository.save(tarefa);

                return new TarefaDTO(tarefa);
            default:
                throw new CodStatusException("Erro na atualização de status: Código incompatível!");
        }
    }
}
