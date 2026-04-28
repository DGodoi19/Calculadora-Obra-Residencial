package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

@Embeddable
public class Buraco {

    @Positive(message = "Largura deve ser positiva")
    private Double largura;

    @Positive(message = "Altura deve ser positiva")
    private Double altura;

    public Buraco() {}

    public Buraco(Double largura, Double altura) {
        this.largura = largura;
        this.altura = altura;
    }

    public Double calcularArea() {
        if (largura == null || altura == null) return 0.0;
        return largura * altura;
    }

    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
}
