package com.example.crud.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken;
    private long expiresIn;
    private UsuarioResponseDTO user;
}