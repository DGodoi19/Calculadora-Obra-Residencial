package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do cômodo é obrigatório")
    private String nome;

    @Positive(message = "Padrão de parede deve ser positivo")
    private Double padraoParede;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comodo_id")
    private List<Parede> paredes = new ArrayList<>();

    public Comodo() {}

    public Comodo(String nome, Double padraoParede) {
        this.nome = nome;
        this.padraoParede = padraoParede;
    }

    public Double calcularAreaTotal() {
        if (paredes == null || paredes.isEmpty()) return 0.0;
        return paredes.stream()
                .mapToDouble(p -> p.getComprimento() != null ? p.getComprimento() * padraoParede : 0.0)
                .sum();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPadraoParede() { return padraoParede; }
    public void setPadraoParede(Double padraoParede) { this.padraoParede = padraoParede; }

    public List<Parede> getParedes() { return paredes; }
    public void setParedes(List<Parede> paredes) { this.paredes = paredes; }
}
