package com.example.CalculadoraObraResidencial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double PadraoParede;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Parede> paredes;

    public Double calcularAreaTotal(){
        return paredes.stream()
                .mapToDouble(p -> p.getComprimento() * PadraoParede)
                .sum();

    }
}
