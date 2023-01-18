package com.todolist.dtos;

import com.todolist.service.validation.UsuarioCreateValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@UsuarioCreateValid
public class UsuarioCreateDTO extends UsuarioDTO{
}
