package com.mollystore.jugadores.dto;

import lombok.Data;

@Data
public class MazoPreferidoDTO {
    private Long id;
    private Long jugadorId;
    private String nombreMazo;
    private String arquetipo;
    private String formato;
}
