package com.todolist.repository;

import com.todolist.entities.Tarefa;
import com.todolist.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query(value = "select * from tarefa taf where taf.usuario = :id", nativeQuery = true)
    List<Tarefa> listaPorUsuario(Long id);
}
