package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;


@Entity
public class Parede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private Double comprimento;
    private Double largura;
    private Double altura;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "largura", column = @Column(name = "largura_porta") ),
            @AttributeOverride(name = "altura", column = @Column(name = "altura_porta") )
    })
    private Buraco porta;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "largura", column = @Column(name = "largura_janela") ),
            @AttributeOverride(name = "altura", column = @Column(name = "altura_janela") )
    })
    private Buraco janela;

    public Double calcularVolumeConcreto(){
        if(this.largura == null || this.altura == null) return 0.0;
        return (this.comprimento * this.largura * this.altura);
    }

    public Double calcularAreaDesconto(){
        Double areaBruta = (comprimento != null && altura != null) ? comprimento * altura : 0.0;
        Double descontoPorta = 0.0;
        Double descontoJanela = 0.0;

        if(porta != null){
            descontoPorta = porta.getLargura() * porta.getAltura();
        }if(janela != null){
            descontoJanela = janela.getLargura() * janela.getAltura();
        }

        return areaBruta - (descontoPorta + descontoJanela);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Buraco getPorta() {
        return porta;
    }

    public void setPorta(Buraco porta) {
        this.porta = porta;
    }

    public Buraco getJanela() {
        return janela;
    }

    public void setJanela(Buraco janela) {
        this.janela = janela;
    }

    public Parede(long ID, Double comprimento, Double largura, Double altura, Buraco porta, Buraco janela) {
        this.ID = ID;
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        this.porta = porta;
        this.janela = janela;
    }

    public Parede() {
    }
}


