package com.todolist.service;

import com.todolist.dtos.TarefaDTO;
import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.enums.TarefaStatus;
import com.todolist.exceptions.ResourceNotFoundException;
import com.todolist.exceptions.CodStatusException;
import com.todolist.repository.TarefaRepository;
import com.todolist.service.utils.StandardResponse;
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

    public StandardResponse create(TarefaDTO tarefaDto){
        Tarefa tarefa = new Tarefa();
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(tarefaDto, tarefa);
        BeanUtils.copyProperties(tarefaDto.getUsuario(), usuario);
        tarefa.setUsuario(usuario);
        tarefaRepository.save(tarefa);

        StandardResponse standardResponse = new StandardResponse();
        standardResponse.setObject(tarefa);

        return standardResponse;
    }

    public StandardResponse update(Long id, TarefaDTO tarefaDto){
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaDto, tarefa);
        return tarefaRepository.findById(id).map(Record ->{
            Record.setDataCadastro(tarefa.getDataCadastro());
            Record.setStatusTarefa(tarefa.getStatusTarefa());
            Record.setDescricao(tarefa.getDescricao());
            Record.setDataPrazo(tarefa.getDataPrazo());
            Record.setDataTermino(tarefa.getDataTermino());
            tarefaRepository.save(Record);

            StandardResponse standardResponse = new StandardResponse();
            standardResponse.setObject(Record);

            return standardResponse;
        }).orElseThrow(()-> new ResourceNotFoundException("Tarefa não encontrada!"));
    }

    public StandardResponse delete(Long id){
        tarefaRepository.deleteById(id);

        StandardResponse standardResponse = new StandardResponse();

        return standardResponse;
    }

    public StandardResponse atualizaStatus(Long idTarefa, int codStatus){

        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada!"));

        StandardResponse standardResponse = new StandardResponse();

        switch (codStatus){
            case 1:
                tarefa.setStatusTarefa(TarefaStatus.CONCLUIDA);
                tarefaRepository.save(tarefa);
                return standardResponse;
            case 2:
                tarefa.setStatusTarefa(TarefaStatus.PENDENTE);
                tarefaRepository.save(tarefa);
                return standardResponse;
            case 3:
                tarefa.setStatusTarefa(TarefaStatus.VENCIDA);
                tarefaRepository.save(tarefa);
                return standardResponse;
            default:
                throw new CodStatusException("Erro na atualização de status: Código incompatível!");
        }
    }
}
