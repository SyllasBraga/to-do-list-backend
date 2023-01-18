package com.todolist.service;

import com.todolist.dtos.TarefaCreateDTO;
import com.todolist.dtos.TarefaDTO;
import com.todolist.dtos.UsuarioCreateDTO;
import com.todolist.dtos.UsuarioDTO;
import com.todolist.entities.Usuario;
import com.todolist.exceptions.ResourceNotFoundException;
import com.todolist.exceptions.TaskNotAcceptableException;
import com.todolist.repository.UsuarioRepository;
import com.todolist.service.utils.CalculaListaTarefas;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    CalculaListaTarefas calcTarefas;
    TarefaService tarefaService;

    public UsuarioService(UsuarioRepository usuarioRepository, CalculaListaTarefas calcTarefas, TarefaService tarefaService) {
        this.usuarioRepository = usuarioRepository;
        this.calcTarefas = calcTarefas;
        this.tarefaService = tarefaService;
    }

    public List<UsuarioDTO> getAll(){
        List<Usuario> listaComTarefas = new ArrayList<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
            listaComTarefas.add(usuario);
        }
        return listaComTarefas.stream().map(x -> new UsuarioDTO(x, x.getTarefas())).collect(Collectors.toList());
    }

    public UsuarioDTO getById(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));

        return new UsuarioDTO(usuario, usuario.getTarefas());
    }

    public String create(UsuarioCreateDTO usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        usuarioRepository.save(usuario);
        return "Criado com sucesso!";
    }

    public String update(Long id, UsuarioDTO usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return usuarioRepository.findById(id).map(Record ->{
            Record.setNome(usuario.getNome());
            Record.setLogin(usuario.getLogin());
            Record.setSenha(usuario.getSenha());
            usuarioRepository.save(Record);
            return "Atualizado com sucesso!";
        }).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public String delete(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuarioRepository.deleteById(usuario.getId());
        return "Sucesso na deleção!";
    }

    public String createTarefa(Long idUsuario, TarefaCreateDTO tarefaDto){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario);
        tarefaDto.setUsuario(usuarioDto);
        tarefaService.create(tarefaDto);
        return "Tarefa criada com sucesso!";
    }

    public String updateTarefa(Long idUsuario, Long idTarefa, TarefaDTO tarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario, usuario.getTarefas());
        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            tarefa.setUsuario(usuarioDto);
            tarefaService.update(idTarefa, tarefa);
            return "Atualizado com sucesso!";
        }else {
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }

    public String deleteTarefa(Long idUsuario, Long idTarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario, usuario.getTarefas());
        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            tarefaService.delete(idTarefa);
            return "Tarefa excluída com sucesso!";
        }else{
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }

    public String updateStatusTarefa(Long idUsuario, Long idTarefa, int codStatus){

        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario, usuario.getTarefas());
        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            return tarefaService.atualizaStatus(idTarefa, codStatus);
        }else{
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }
}
