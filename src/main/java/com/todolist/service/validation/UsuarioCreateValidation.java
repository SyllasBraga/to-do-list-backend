package com.todolist.service.validation;

import com.todolist.controller.exceptions.FieldMessage;
import com.todolist.dtos.UsuarioCreateDTO;
import com.todolist.dtos.UsuarioDTO;
import com.todolist.entities.Usuario;
import com.todolist.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UsuarioCreateValidation implements ConstraintValidator<UsuarioCreateValid, UsuarioCreateDTO> {

    private UsuarioRepository usuarioRepository;

    @Override
    public void initialize(UsuarioCreateValid ann) {
    }

    public UsuarioCreateValidation(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean isValid(UsuarioCreateDTO usuarioDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        Usuario usuario = usuarioRepository.findByLogin(usuarioDTO.getLogin());

        if (usuario != null){
            list.add(new FieldMessage("login", "Esse login já existe!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getError()).addPropertyNode(e.getNomeCampo())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
