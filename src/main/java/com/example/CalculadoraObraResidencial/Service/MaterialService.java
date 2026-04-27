package com.example.CalculadoraObraResidencial.Service;

import com.example.CalculadoraObraResidencial.Entities.Parede;
import com.example.CalculadoraObraResidencial.Repository.ParedeRepository;
import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private ParedeRepository repository;

    public String calcularProjetoCompleto(ProjetoCalculoDTO projeto) {
        List<Parede> paredes = repository.findAllById(projeto.getParedesIds());

        if (paredes.isEmpty()) {
            return "Nenhuma parede encontrada para os IDs informados.";
        }

        double volumeConcretoTotal = paredes.stream()
                .mapToDouble(p -> p.getComprimento() * p.getLargura() * projeto.getAlturaVigaBaldrame())
                .sum();

        double areaLiquidaTotal = paredes.stream()
                .mapToDouble(Parede::calcularAreaDesconto)
                .sum();

        double areaUnitáriaTijolo = projeto.getComprimentoTijolos() * projeto.getAlturaTijolos();
        long quantidadeTijolos = (long) Math.ceil(areaLiquidaTotal / areaUnitáriaTijolo);

        return String.format(
                "--- RELATÓRIO DE MATERIAIS (PROCESSADO NO SERVICE) ---\n" +
                        "Paredes Analisadas: %d\n" +
                        "Volume de Concreto: %.2f m³\n" +
                        "Total de Tijolos: %d unidades\n" +
                        "Área Líquida Total: %.2f m²",
                paredes.size(), volumeConcretoTotal, quantidadeTijolos, areaLiquidaTotal
        );
    }
}