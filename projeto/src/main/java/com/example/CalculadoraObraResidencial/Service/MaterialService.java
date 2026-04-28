package com.example.CalculadoraObraResidencial.Service;

import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import com.example.CalculadoraObraResidencial.DTO.ResultadoCalculoDTO;
import com.example.CalculadoraObraResidencial.Entities.Parede;
import com.example.CalculadoraObraResidencial.Exception.RecursoNaoEncontradoException;
import com.example.CalculadoraObraResidencial.Repository.ParedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        double volumeConcretoTotal = paredes.stream()
                .mapToDouble(p -> p.getComprimento() * p.getLargura() * projeto.getAlturaVigaBaldrame())
                .sum();

        double areaLiquidaTotal = paredes.stream()
                .mapToDouble(Parede::calcularAreaLiquida)
                .sum();

        double areaUnitariaTijolo = projeto.getComprimentoTijolo() * projeto.getAlturaTijolo();
        long quantidadeTijolos = areaUnitariaTijolo > 0
                ? (long) Math.ceil(areaLiquidaTotal / areaUnitariaTijolo)
                : 0L;

        return new ResultadoCalculoDTO(
                paredes.size(),
                areaLiquidaTotal,
                volumeConcretoTotal,
                quantidadeTijolos
        );
    }
}
