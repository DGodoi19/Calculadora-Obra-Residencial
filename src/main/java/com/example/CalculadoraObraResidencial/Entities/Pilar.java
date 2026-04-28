package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class Pilar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do pilar é obrigatório")
    private String nome;

    @Positive(message = "Largura deve ser positiva")
    private Double largura;

    @Positive(message = "Profundidade deve ser positiva")
    private Double profundidade;

    @Positive(message = "Altura deve ser positiva")
    private Double altura;

    public Pilar() {}

    public Pilar(String nome, Double largura, Double profundidade, Double altura) {
        this.nome = nome;
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
    }

    public Double calcularVolume() {
        if (largura == null || profundidade == null || altura == null) return 0.0;
        return largura * profundidade * altura;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }

    public Double getProfundidade() { return profundidade; }
    public void setProfundidade(Double profundidade) { this.profundidade = profundidade; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
}
