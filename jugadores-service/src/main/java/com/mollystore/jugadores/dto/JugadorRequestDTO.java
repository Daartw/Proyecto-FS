package com.mollystore.jugadores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JugadorRequestDTO {
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    @Email private String email;
    @NotBlank private String telefono;
}
