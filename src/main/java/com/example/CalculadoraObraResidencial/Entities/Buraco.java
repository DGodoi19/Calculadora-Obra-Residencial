package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;


@Embeddable
public class Buraco {

    private Double largura;
    private Double altura;


    public Double calcularAreaBuraco() {
        if (largura == null || altura == null) return 0.0;
        return largura * altura;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Buraco(Double largura, Double altura) {
        this.largura = largura;
        this.altura = altura;
    }

    public Buraco() {
    }
}