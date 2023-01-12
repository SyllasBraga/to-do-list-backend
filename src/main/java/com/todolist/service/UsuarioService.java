package com.todolist.service;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.repository.UsuarioRepository;
import com.todolist.service.utils.CalculaListaTarefas;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        return usuario;
    }

    public String create(Usuario usuario){
        usuarioRepository.save(usuario);
        return "Criado com sucesso!";
    }

    public String update(Long id, Usuario usuario){
        return usuarioRepository.findById(id).map(Record ->{
            Record.setNome(usuario.getNome());
            Record.setLogin(usuario.getLogin());
            Record.setSenha(usuario.getSenha());
            usuarioRepository.save(Record);
            return "Atualizado com sucesso!";
        }).orElse("Erro na atualização!");
    }

    public String delete(Long id){
        usuarioRepository.deleteById(id);
        return "Sucesso na deleção!";
    }

    public String createTarefa(Long idUsuario, Tarefa tarefa){
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        tarefa.setUsuario(usuario);
        tarefaService.create(tarefa);
        return "Tarefa criada com sucesso!";
    }

    public String updateTarefa(Long idUsuario, Long idTarefa, Tarefa tarefa){
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        tarefa.setUsuario(usuario);
        tarefaService.update(idTarefa, tarefa);
        return "Atualizado com sucesso!";
    }

    public String deleteTarefa(Long idUsuario, Long idTarefa){
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        usuario.setTarefas(calcTarefas.recuperaListaTarefas(usuario));
        if (usuario.getTarefas().contains(tarefaService.getById(idTarefa))){
            tarefaService.delete(idTarefa);
            return "Tarefa excluída com sucesso!";
        }else{
            return "Erro na deleção!";
        }
    }
}
