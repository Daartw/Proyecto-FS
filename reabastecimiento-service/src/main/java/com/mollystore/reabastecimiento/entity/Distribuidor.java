package com.mollystore.reabastecimiento.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Distribuidor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nombre;
    private String contacto;
    private String pais;
    private Integer stockMinimoActivacion;
}
