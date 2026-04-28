package com.example.CalculadoraObraResidencial.DTO;

public class ResultadoCalculoDTO {

    private int paredesAnalisadas;
    private Double areaLiquidaTotal;
    private Double volumeConcretoM3;
    private Long quantidadeTijolos;

    public ResultadoCalculoDTO() {}

    public ResultadoCalculoDTO(int paredesAnalisadas, Double areaLiquidaTotal,
                               Double volumeConcretoM3, Long quantidadeTijolos) {
        this.paredesAnalisadas = paredesAnalisadas;
        this.areaLiquidaTotal = areaLiquidaTotal;
        this.volumeConcretoM3 = volumeConcretoM3;
        this.quantidadeTijolos = quantidadeTijolos;
    }

    public int getParedesAnalisadas() { return paredesAnalisadas; }
    public void setParedesAnalisadas(int paredesAnalisadas) { this.paredesAnalisadas = paredesAnalisadas; }

    public Double getAreaLiquidaTotal() { return areaLiquidaTotal; }
    public void setAreaLiquidaTotal(Double areaLiquidaTotal) { this.areaLiquidaTotal = areaLiquidaTotal; }

    public Double getVolumeConcretoM3() { return volumeConcretoM3; }
    public void setVolumeConcretoM3(Double volumeConcretoM3) { this.volumeConcretoM3 = volumeConcretoM3; }

    public Long getQuantidadeTijolos() { return quantidadeTijolos;  }
    public void setQuantidadeTijolos(Long quantidadeTijolos) { this.quantidadeTijolos = quantidadeTijolos; }
}
