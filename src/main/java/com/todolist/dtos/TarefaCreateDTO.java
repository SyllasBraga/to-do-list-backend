package com.todolist.dtos;

import com.todolist.service.validation.TarefaCreateValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@TarefaCreateValid
@Data
@NoArgsConstructor
public class TarefaCreateDTO extends TarefaDTO{
}
