package com.todolist.service.validation;

import com.todolist.controller.exceptions.FieldMessage;
import com.todolist.dtos.TarefaCreateDTO;
import com.todolist.repository.TarefaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class TarefaCreateValidation implements ConstraintValidator<TarefaCreateValid, TarefaCreateDTO> {

    @Override
    public void initialize(TarefaCreateValid ann) {
    }

    @Override
    public boolean isValid(TarefaCreateDTO tarefaCreateDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (tarefaCreateDTO.getDataPrazo().after(tarefaCreateDTO.getDataCadastro())) {
            list.add(new FieldMessage("dataPrazo", "O prazo não pode ser menor que a data inicial!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getError()).addPropertyNode(e.getNomeCampo())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
