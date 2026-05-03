package com.mollystore.catalogo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Carta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nombreIngles;
    @NotBlank private String nombreEspanol;
    private String descripcionIngles;
    private String descripcionEspanol;
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expansion_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Expansion expansion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rareza_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Rareza rareza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private TipoCarta tipo;
}
