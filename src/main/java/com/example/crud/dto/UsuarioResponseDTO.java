package com.example.crud.dto;

import com.example.crud.model.Papel;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private Papel papel;
}
