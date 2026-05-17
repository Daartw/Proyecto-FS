package com.mollystore.alertas.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class JugadorResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private Integer puntosAcumulados;
}
