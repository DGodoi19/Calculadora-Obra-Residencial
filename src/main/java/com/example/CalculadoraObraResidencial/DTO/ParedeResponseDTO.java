package com.example.CalculadoraObraResidencial.DTO;

public class ParedeResponseDTO {

    private Long id;
    private Double comprimento;
    private Double largura;
    private Double altura;
    private BuracoDTO porta;
    private BuracoDTO janela;
    private Double areaLiquida;
    private Double volumeConcreto;

    public ParedeResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Double getAreaLiquida() { return areaLiquida; }
    public void setAreaLiquida(Double areaLiquida) { this.areaLiquida = areaLiquida; }

    public Double getVolumeConcreto() { return volumeConcreto; }
    public void setVolumeConcreto(Double volumeConcreto) { this.volumeConcreto = volumeConcreto; }
}
