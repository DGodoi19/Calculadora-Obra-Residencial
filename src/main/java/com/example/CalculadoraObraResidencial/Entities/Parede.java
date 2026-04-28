package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Parede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Comprimento é obrigatório")
    @Positive(message = "Comprimento deve ser positivo")
    private Double comprimento;

    @NotNull(message = "Largura é obrigatória")
    @Positive(message = "Largura deve ser positiva")
    private Double largura;

    @NotNull(message = "Altura é obrigatória")
    @Positive(message = "Altura deve ser positiva")
    private Double altura;

    @Valid
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "largura", column = @Column(name = "largura_porta")),
            @AttributeOverride(name = "altura",  column = @Column(name = "altura_porta"))
    })
    private Buraco porta;

    @Valid
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "largura", column = @Column(name = "largura_janela")),
            @AttributeOverride(name = "altura",  column = @Column(name = "altura_janela"))
    })
    private Buraco janela;

    public Parede() {}

    public Parede(Double comprimento, Double largura, Double altura, Buraco porta, Buraco janela) {
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        this.porta = porta;
        this.janela = janela;
    }

    public Double calcularVolumeConcreto() {
        if (comprimento == null || largura == null || altura == null) return 0.0;
        return comprimento * largura * altura;
    }

    public Double calcularAreaLiquida() {
        double areaBruta = (comprimento != null && altura != null) ? comprimento * altura : 0.0;
        double descontoPorta  = (porta  != null) ? porta.calcularArea()  : 0.0;
        double descontoJanela = (janela != null) ? janela.calcularArea() : 0.0;
        return areaBruta - descontoPorta - descontoJanela;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getComprimento() { return comprimento; }
    public void setComprimento(Double comprimento) { this.comprimento = comprimento; }

    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }

    public Buraco getPorta() { return porta; }
    public void setPorta(Buraco porta) { this.porta = porta; }

    public Buraco getJanela() { return janela; }
    public void setJanela(Buraco janela) { this.janela = janela; }
}
