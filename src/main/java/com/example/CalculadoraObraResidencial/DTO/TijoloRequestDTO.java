package com.example.CalculadoraObraResidencial.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class TijoloRequestDTO {

    @NotEmpty(message = "Lista de IDs não pode ser vazia")
    private List<Long> ids;

    @NotNull(message = "Altura do tijolo é obrigatória")
    @Positive(message = "Altura do tijolo deve ser positiva")
    private Double alturaTijolo;

    @NotNull(message = "Comprimento do tijolo é obrigatório")
    @Positive(message = "Comprimento do tijolo deve ser positivo")
    private Double comprimentoTijolo;

    public TijoloRequestDTO() {}

    public List<Long> getIds() { return ids; }
    public void setIds(List<Long> ids) { this.ids = ids; }

    public Double getAlturaTijolo() { return alturaTijolo; }
    public void setAlturaTijolo(Double alturaTijolo) { this.alturaTijolo = alturaTijolo; }

    public Double getComprimentoTijolo() { return comprimentoTijolo; }
    public void setComprimentoTijolo(Double comprimentoTijolo) { this.comprimentoTijolo = comprimentoTijolo; }
}
