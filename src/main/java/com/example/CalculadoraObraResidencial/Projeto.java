package com.example.CalculadoraObraResidencial;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Projeto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nomeProjeto;

        @OneToMany(cascade = CascadeType.ALL)
        private List<Comodo> comodos;

        @OneToMany(cascade = CascadeType.ALL)
        private List<Pilar> pilares;

}
