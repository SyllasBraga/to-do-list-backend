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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
        List<UsuarioDTO> listaDto = new ArrayList<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
            listaComTarefas.add(usuario);
        }

        listaDto = listaComTarefas.stream().map(
                x -> new UsuarioDTO(x, x.getTarefas())).collect(Collectors.toList());

        return listaDto;
    }


    public UsuarioDTO getById(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario, usuario.getTarefas());

        return usuarioDTO;
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        usuarioRepository.save(usuario);

        return usuarioDto;
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return usuarioRepository.findById(id).map(Record ->{
            Record.setNome(usuario.getNome());
            Record.setLogin(usuario.getLogin());
            Record.setSenha(usuario.getSenha());
            usuarioRepository.save(Record);

            return usuarioDto;
        }).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public String delete(Long id){
        UsuarioDTO usuario = getById(id);

        usuarioRepository.deleteById(usuario.getId());

        return "O usuário "+ usuario.getNome() + " foi deletado do sistema com sucesso.";
    }

    public UsuarioDTO createTarefa(Long idUsuario, TarefaCreateDTO tarefaDto){

        UsuarioDTO usuarioDto = getById(idUsuario);

        tarefaDto.setUsuario(usuarioDto);
        tarefaService.create(tarefaDto);
        usuarioDto.getTarefas().add(tarefaDto);

        return usuarioDto;
    }

    public UsuarioDTO updateTarefa(Long idUsuario, Long idTarefa, TarefaDTO tarefa){

        UsuarioDTO usuarioDto = getById(idUsuario);
        Usuario usuario = new Usuario();

        BeanUtils.copyProperties(usuarioDto, usuario);

        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        BeanUtils.copyProperties(usuario.getTarefas(), usuarioDto.getTarefas());

        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){

            tarefa.setUsuario(usuarioDto);
            tarefaService.update(idTarefa, tarefa);
            return usuarioDto;

        }else {
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }

    public String deleteTarefa(Long idUsuario, Long idTarefa){

        UsuarioDTO usuarioDto = getById(idUsuario);

        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            return tarefaService.delete(idTarefa);
        }else{
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }

    public UsuarioDTO updateStatusTarefa(Long idUsuario, Long idTarefa, int codStatus){

        UsuarioDTO usuarioDto = getById(idUsuario);

        if (usuarioDto.getTarefas().contains(tarefaService.getById(idTarefa))){
            TarefaDTO tarefaDTO = tarefaService.atualizaStatus(idTarefa, codStatus);

            return usuarioDto;
        }else{
            throw new TaskNotAcceptableException("Essa tarefa não pertence a este usuário!");
        }
    }
}
