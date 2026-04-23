package com.example.CalculadoraObraResidencial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Buraco {

    private Double largura;
    private Double altura;


    public Double calcularAreaBuraco() {
        if (largura == null || altura == null) return 0.0;
        return largura * altura;
    }
}