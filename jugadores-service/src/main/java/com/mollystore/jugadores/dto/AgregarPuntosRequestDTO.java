package com.mollystore.jugadores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AgregarPuntosRequestDTO {
    @Positive private Integer puntos;
    @NotBlank private String motivo;
}
