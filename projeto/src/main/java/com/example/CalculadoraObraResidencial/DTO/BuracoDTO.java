package com.example.CalculadoraObraResidencial.DTO;

import jakarta.validation.constraints.Positive;

public class BuracoDTO {

    @Positive(message = "Largura deve ser positiva")
    private Double largura;

    @Positive(message = "Altura deve ser positiva")
    private Double altura;

    public BuracoDTO() {}

    public BuracoDTO(Double largura, Double altura) {
        this.largura = largura;
        this.altura = altura;
    }

    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
}
