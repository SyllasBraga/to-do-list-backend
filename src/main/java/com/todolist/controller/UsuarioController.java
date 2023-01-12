package com.todolist.controller;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import com.todolist.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.create(usuario));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.delete(id));
    }

    @PostMapping(path = "/{id}/tarefas")
    public ResponseEntity<String> createTarefa(@PathVariable(name = "id") Long idUsuario,@RequestBody Tarefa tarefa){
        return ResponseEntity.ok(usuarioService.createTarefa(idUsuario, tarefa));
    }

    @PutMapping(path = "/{idUsuario}/tarefas/{idTarefa}")
    public ResponseEntity<String> updateTarefa(@PathVariable(name = "idUsuario") Long idUsuario,
                                               @PathVariable(name = "idTarefa") Long idTarefa,
                                               @RequestBody Tarefa tarefa){
        return ResponseEntity.ok(usuarioService.updateTarefa(idUsuario, idTarefa, tarefa));
    }
}
