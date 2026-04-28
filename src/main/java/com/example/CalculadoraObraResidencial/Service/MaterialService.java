package com.example.CalculadoraObraResidencial.Service;

import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import com.example.CalculadoraObraResidencial.DTO.ResultadoCalculoDTO;
import com.example.CalculadoraObraResidencial.Entities.Parede;
import com.example.CalculadoraObraResidencial.Exception.RecursoNaoEncontradoException;
import com.example.CalculadoraObraResidencial.Repository.ParedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private ParedeRepository repository;

    public ResultadoCalculoDTO calcularProjetoCompleto(ProjetoCalculoDTO projeto) {
        List<Parede> paredes = repository.findAllById(projeto.getParedeIds());

        if (paredes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma parede encontrada para os IDs informados.");
        }

        // Usa BigDecimal para evitar erros de ponto flutuante (ex: 0.30000000000000004)
        BigDecimal volumeConcreto = paredes.stream()
                .map(p -> bd(p.getComprimento())
                        .multiply(bd(p.getLargura()))
                        .multiply(bd(projeto.getAlturaVigaBaldrame())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal areaLiquida = paredes.stream()
                .map(p -> bd(p.calcularAreaLiquida()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal areaUnitariaTijolo = bd(projeto.getComprimentoTijolo())
                .multiply(bd(projeto.getAlturaTijolo()));

        long quantidadeTijolos = 0;
        if (areaUnitariaTijolo.compareTo(BigDecimal.ZERO) > 0) {
            quantidadeTijolos = areaLiquida
                    .divide(areaUnitariaTijolo, 10, RoundingMode.HALF_UP)
                    .setScale(0, RoundingMode.CEILING)
                    .longValue();
        }

        return new ResultadoCalculoDTO(
                paredes.size(),
                round2(areaLiquida),
                round2(volumeConcreto),
                quantidadeTijolos
        );
    }

    private BigDecimal bd(Double value) {
        if (value == null) return BigDecimal.ZERO;
        return BigDecimal.valueOf(value);
    }

    private Double round2(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}