package com.todolist.service;

import com.todolist.entities.Usuario;
import com.todolist.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getById(Long id){
        return usuarioRepository.findById(id).get();
    }

    public String create(Usuario usuario){
        usuarioRepository.save(usuario);
        return "Criado com sucesso!";
    }

    public String update(Long id, Usuario usuario){
        return usuarioRepository.findById(id).map(Record ->{
            Record.setNome(usuario.getNome());
            Record.setLogin(usuario.getNome());
            Record.setSenha(usuario.getSenha());
            return "Atualizado com sucesso!";
        }).orElse("Erro na atualização!");
    }

    public String delete(Long id){
        usuarioRepository.deleteById(id);
        return "Sucesso na deleção!";
    }
}
