package com.example.CalculadoraObraResidencial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}


