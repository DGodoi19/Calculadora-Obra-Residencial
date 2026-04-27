package com.example.CalculadoraObraResidencial.DTO;


import java.util.List;


public class ProjetoCalculoDTO {
    private List<Long> paredesIds;
    private Double alturaVigaBaldrame;

    private Double comprimentoTijolos;
    private Double alturaTijolos;

    public List<Long> getParedesIds() {
        return paredesIds;
    }

    public void setParedesIds(List<Long> paredesIds) {
        this.paredesIds = paredesIds;
    }

    public Double getAlturaVigaBaldrame() {
        return alturaVigaBaldrame;
    }

    public void setAlturaVigaBaldrame(Double alturaVigaBaldrame) {
        this.alturaVigaBaldrame = alturaVigaBaldrame;
    }

    public Double getComprimentoTijolos() {
        return comprimentoTijolos;
    }

    public void setComprimentoTijolos(Double comprimentoTijolos) {
        this.comprimentoTijolos = comprimentoTijolos;
    }

    public Double getAlturaTijolos() {
        return alturaTijolos;
    }

    public void setAlturaTijolos(Double alturaTijolos) {
        this.alturaTijolos = alturaTijolos;
    }

    public ProjetoCalculoDTO(List<Long> paredesIds, Double alturaVigaBaldrame, Double comprimentoTijolos, Double alturaTijolos) {
        this.paredesIds = paredesIds;
        this.alturaVigaBaldrame = alturaVigaBaldrame;
        this.comprimentoTijolos = comprimentoTijolos;
        this.alturaTijolos = alturaTijolos;
    }

    public ProjetoCalculoDTO() {
    }
}
