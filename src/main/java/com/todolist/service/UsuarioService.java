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
import com.todolist.service.utils.StandardResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public StandardResponse getAll(){
        List<Usuario> listaComTarefas = new ArrayList<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
            listaComTarefas.add(usuario);
        }

        StandardResponse standardResponse = new StandardResponse();
        standardResponse.setObject(listaComTarefas.stream().map(
                x -> new UsuarioDTO(x, x.getTarefas())).collect(Collectors.toList()));

        return standardResponse;
    }

    public StandardResponse getById(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));

        StandardResponse standardResponse = new StandardResponse();
        standardResponse.setObject(new UsuarioDTO(usuario, usuario.getTarefas()));

        return standardResponse;
    }

    public StandardResponse create(UsuarioCreateDTO usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        usuarioRepository.save(usuario);

        StandardResponse standardResponse = new StandardResponse();
        standardResponse.setObject(usuario);

        return standardResponse;
    }

    public StandardResponse update(Long id, UsuarioDTO usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return usuarioRepository.findById(id).map(Record ->{
            Record.setNome(usuario.getNome());
            Record.setLogin(usuario.getLogin());
            Record.setSenha(usuario.getSenha());
            usuarioRepository.save(Record);

            StandardResponse standardResponse = new StandardResponse();
            standardResponse.setObject(Record);

            return standardResponse;
        }).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public StandardResponse delete(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuarioRepository.deleteById(usuario.getId());

        StandardResponse standardResponse = new StandardResponse();

        return standardResponse;
    }

    public StandardResponse createTarefa(Long idUsuario, TarefaCreateDTO tarefaDto){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario);
        tarefaDto.setUsuario(usuarioDto);

        return tarefaService.create(tarefaDto);
    }

    public StandardResponse updateTarefa(Long idUsuario, Long idTarefa, TarefaDTO tarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario, usuario.getTarefas());
        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            tarefa.setUsuario(usuarioDto);

            return tarefaService.update(idTarefa, tarefa);
        }else {
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }

    public StandardResponse deleteTarefa(Long idUsuario, Long idTarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        UsuarioDTO usuarioDto = new UsuarioDTO(usuario, usuario.getTarefas());
        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            return tarefaService.delete(idTarefa);
        }else{
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }

    public StandardResponse updateStatusTarefa(Long idUsuario, Long idTarefa, int codStatus){

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
