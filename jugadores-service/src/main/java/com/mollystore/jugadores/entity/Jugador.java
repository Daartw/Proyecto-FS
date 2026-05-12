package com.mollystore.jugadores.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Jugador {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    @Email private String email;
    @NotBlank private String telefono;
    private LocalDate fechaRegistro;
    private Integer puntosAcumulados;
    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<MazoPreferido> mazosPreferidos;
}
