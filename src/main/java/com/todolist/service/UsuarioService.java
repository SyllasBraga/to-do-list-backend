package com.todolist.service;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.exceptions.ResourceNotFoundException;
import com.todolist.repository.UsuarioRepository;
import com.todolist.service.utils.CalculaListaTarefas;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Usuario> getAll(){
        List<Usuario> listaComTarefas = new ArrayList<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
            listaComTarefas.add(usuario);
        }
        return listaComTarefas;
    }

    public Usuario getById(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        return usuario;
    }

    public String create(Usuario usuario){
        usuarioRepository.save(usuario);
        return "Criado com sucesso!";
    }

    public String update(Long id, Usuario usuarioObj){
        return usuarioRepository.findById(id).map(Record ->{
            Record.setNome(usuarioObj.getNome());
            Record.setLogin(usuarioObj.getLogin());
            Record.setSenha(usuarioObj.getSenha());
            usuarioRepository.save(Record);
            return "Atualizado com sucesso!";
        }).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
    }

    public String delete(Long id){
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuarioRepository.deleteById(id);
        return "Sucesso na deleção!";
    }

    public String createTarefa(Long idUsuario, Tarefa tarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        tarefa.setUsuario(usuario);
        tarefaService.create(tarefa);
        return "Tarefa criada com sucesso!";
    }

    public String updateTarefa(Long idUsuario, Long idTarefa, Tarefa tarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        tarefa.setUsuario(usuario);
        tarefaService.update(idTarefa, tarefa);
        return "Atualizado com sucesso!";
    }

    public String deleteTarefa(Long idUsuario, Long idTarefa){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        if (usuario.getTarefas().contains(tarefaService.getById(idTarefa))){
            tarefaService.delete(idTarefa);
            return "Tarefa excluída com sucesso!";
        }else{
            return "Erro na deleção!";
        }
    }

    public String updateStatusTarefa(Long idUsuario, Long idTarefa, int codStatus){

        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        if (usuario.getTarefas().contains(tarefaService.getById(idTarefa))){
            return tarefaService.atualizaStatus(idTarefa, codStatus);
        }else{
            return "Erro na atualização!";
        }

    }
}
