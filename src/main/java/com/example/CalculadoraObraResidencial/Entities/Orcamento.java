package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroOrcamento; // Identificador numérico exigido pelo desafio

    @Column(nullable = false)
    private String nomeUsuario; // Buscador por nome exigido pelo desafio

    private int paredesAnalisadas;
    private Double areaLiquidaTotal;
    private Double volumeConcretoM3;
    private Long quantidadeTijolos;
    private LocalDateTime dataGeracao;

    @PrePersist
    protected void onCreate() {
        this.dataGeracao = LocalDateTime.now();
    }

    // Construtor Padrão necessário para o Hibernate
    public Orcamento() {}

    // Construtor Completo usado para criar instâncias no seu Bean
    public Orcamento(String nomeUsuario, int paredesAnalisadas, Double areaLiquidaTotal, Double volumeConcretoM3, Long quantidadeTijolos) {
        this.nomeUsuario = nomeUsuario;
        this.paredesAnalisadas = paredesAnalisadas;
        this.areaLiquidaTotal = areaLiquidaTotal;
        this.volumeConcretoM3 = volumeConcretoM3;
        this.quantidadeTijolos = quantidadeTijolos;
    }

    // Getters e Setters (Encapsulamento completo [cite: 8])
    public Long getNumeroOrcamento() {
        return numeroOrcamento;
    }

    public void setNumeroOrcamento(Long numeroOrcamento) {
        this.numeroOrcamento = numeroOrcamento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public int getParedesAnalisadas() {
        return paredesAnalisadas;
    }

    public void setParedesAnalisadas(int paredesAnalisadas) {
        this.paredesAnalisadas = paredesAnalisadas;
    }

    public Double getAreaLiquidaTotal() {
        return areaLiquidaTotal;
    }

    public void setAreaLiquidaTotal(Double areaLiquidaTotal) {
        this.areaLiquidaTotal = areaLiquidaTotal;
    }

    public Double getVolumeConcretoM3() {
        return volumeConcretoM3;
    }

    public void setVolumeConcretoM3(Double volumeConcretoM3) {
        this.volumeConcretoM3 = volumeConcretoM3;
    }

    public Long getQuantidadeTijolos() {
        return quantidadeTijolos;
    }

    public void setQuantidadeTijolos(Long quantidadeTijolos) {
        this.quantidadeTijolos = quantidadeTijolos;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }
}