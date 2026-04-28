package com.example.CalculadoraObraResidencial.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class ConcretoRequestDTO {

    @NotEmpty(message = "Lista de IDs não pode ser vazia")
    private List<Long> ids;

    @NotNull(message = "Altura da viga baldrame é obrigatória")
    @Positive(message = "Altura da viga baldrame deve ser positiva")
    private Double alturaViga;

    public ConcretoRequestDTO() {}

    public List<Long> getIds() { return ids; }
    public void setIds(List<Long> ids) { this.ids = ids; }

    public Double getAlturaViga() { return alturaViga; }
    public void setAlturaViga(Double alturaViga) { this.alturaViga = alturaViga; }
}
