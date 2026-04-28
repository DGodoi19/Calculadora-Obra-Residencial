package com.example.CalculadoraObraResidencial.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ParedeRequestDTO {

    @NotNull @Positive
    private Double comprimento;

    @NotNull @Positive
    private Double largura;

    @NotNull @Positive
    private Double altura;

    @Valid
    private BuracoDTO porta;

    @Valid
    private BuracoDTO janela;

    public ParedeRequestDTO() {}

    public Double getComprimento() { return comprimento; }
    public void setComprimento(Double comprimento) { this.comprimento = comprimento; }

    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }

    public BuracoDTO getPorta() { return porta; }
    public void setPorta(BuracoDTO porta) { this.porta = porta; }

    public BuracoDTO getJanela() { return janela; }
    public void setJanela(BuracoDTO janela) { this.janela = janela; }
}
