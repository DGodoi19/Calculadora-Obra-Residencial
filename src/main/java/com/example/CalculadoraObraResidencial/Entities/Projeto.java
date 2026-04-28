package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do projeto é obrigatório")
    private String nomeProjeto;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "projeto_id")
    private List<Comodo> comodos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "projeto_id")
    private List<Pilar> pilares = new ArrayList<>();

    public Projeto() {}

    public Projeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeProjeto() { return nomeProjeto; }
    public void setNomeProjeto(String nomeProjeto) { this.nomeProjeto = nomeProjeto; }

    public List<Comodo> getComodos() { return comodos; }
    public void setComodos(List<Comodo> comodos) { this.comodos = comodos; }

    public List<Pilar> getPilares() { return pilares; }
    public void setPilares(List<Pilar> pilares) { this.pilares = pilares; }
}
