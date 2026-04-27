package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPadraoParede() {
        return PadraoParede;
    }

    public void setPadraoParede(Double padraoParede) {
        PadraoParede = padraoParede;
    }

    public List<Parede> getParedes() {
        return paredes;
    }

    public void setParedes(List<Parede> paredes) {
        this.paredes = paredes;
    }

    public Comodo(Long id, List<Parede> paredes, String nome, Double padraoParede) {
        this.id = id;
        this.paredes = paredes;
        this.nome = nome;
        PadraoParede = padraoParede;
    }

    public Comodo() {
    }
}
