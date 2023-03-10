package com.todolist.controller;

import com.todolist.dtos.TarefaCreateDTO;
import com.todolist.dtos.TarefaDTO;
import com.todolist.dtos.UsuarioCreateDTO;
import com.todolist.dtos.UsuarioDTO;
import com.todolist.service.UsuarioService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<UsuarioDTO>> getAll(){
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioDto){
        return ResponseEntity.ok(usuarioService.create(usuarioDto));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuario){
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.delete(id));
    }

    @PostMapping(path = "/{id}/tarefas")
    public ResponseEntity<UsuarioDTO> createTarefa(@PathVariable(name = "id") Long idUsuario, @Valid @RequestBody TarefaCreateDTO tarefa){
        return ResponseEntity.ok(usuarioService.createTarefa(idUsuario, tarefa));
    }

    @PutMapping(path = "/{idUsuario}/tarefas/{idTarefa}")
    public ResponseEntity<UsuarioDTO> updateTarefa(@PathVariable(name = "idUsuario") Long idUsuario,
                                               @PathVariable(name = "idTarefa") Long idTarefa,
                                               @RequestBody @Valid TarefaDTO tarefa){
        return ResponseEntity.ok(usuarioService.updateTarefa(idUsuario, idTarefa, tarefa));
    }

    @DeleteMapping(path = "/{idUsuario}/tarefas/{idTarefa}")
    public ResponseEntity<String> updateTarefa(@PathVariable(name = "idUsuario") Long idUsuario,
                                               @PathVariable(name = "idTarefa") Long idTarefa){
        return ResponseEntity.ok(usuarioService.deleteTarefa(idUsuario, idTarefa));
    }

    @PutMapping(path = "/{idUsuario}/tarefas/{idTarefa}/updatestatus")
    public ResponseEntity<UsuarioDTO> updateStatusTarefa(@PathVariable(name = "idUsuario") Long idUsuario,
                                                     @PathVariable(name = "idTarefa") Long idTarefa,
                                                     @RequestParam(name = "status") int codStatus){
        return ResponseEntity.ok(usuarioService.updateStatusTarefa(idUsuario, idTarefa, codStatus));
    }
}
