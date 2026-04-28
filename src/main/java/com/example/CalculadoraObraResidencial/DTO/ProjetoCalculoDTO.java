package com.example.CalculadoraObraResidencial.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class ProjetoCalculoDTO {

    @NotEmpty(message = "Lista de IDs de paredes não pode ser vazia")
    private List<Long> paredeIds;

    @NotNull(message = "Altura da viga baldrame é obrigatória")
    @Positive(message = "Altura da viga baldrame deve ser positiva")
    private Double alturaVigaBaldrame;

    @NotNull(message = "Comprimento do tijolo é obrigatório")
    @Positive(message = "Comprimento do tijolo deve ser positivo")
    private Double comprimentoTijolo;

    @NotNull(message = "Altura do tijolo é obrigatória")
    @Positive(message = "Altura do tijolo deve ser positiva")
    private Double alturaTijolo;

    public ProjetoCalculoDTO() {}

    public List<Long> getParedeIds() { return paredeIds; }
    public void setParedeIds(List<Long> paredeIds) { this.paredeIds = paredeIds; }

    public Double getAlturaVigaBaldrame() { return alturaVigaBaldrame; }
    public void setAlturaVigaBaldrame(Double alturaVigaBaldrame) { this.alturaVigaBaldrame = alturaVigaBaldrame; }

    public Double getComprimentoTijolo() { return comprimentoTijolo; }
    public void setComprimentoTijolo(Double comprimentoTijolo) { this.comprimentoTijolo = comprimentoTijolo; }

    public Double getAlturaTijolo() { return alturaTijolo; }
    public void setAlturaTijolo(Double alturaTijolo) { this.alturaTijolo = alturaTijolo; }
}
