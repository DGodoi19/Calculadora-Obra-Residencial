package com.example.CalculadoraObraResidencial.Controller;

import com.example.CalculadoraObraResidencial.Entities.Parede;
import com.example.CalculadoraObraResidencial.Repository.ParedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/paredes")
public class ParedeController {

    @Autowired
    private ParedeRepository repository;

    @PostMapping
    public Parede criarParede(@RequestBody Parede parede) {
        return repository.save(parede);
    }

    @GetMapping
    public List<Parede> listarTodas() {
        return repository.findAll();
    }

    @PostMapping("/calculo/concreto")
    public String calcularVolumeLote(@RequestBody Map<String, Object> payload) {
        List<Integer> idsInt = (List<Integer>) payload.get("ids");
        Double alturaViga = Double.parseDouble(payload.get("alturaViga").toString());

        List<Long> ids = idsInt.stream().map(Integer::longValue).toList();
        List<Parede> paredes = repository.findAllById(ids);

        double volumeTotal = paredes.stream()
                .mapToDouble(p -> (p.getComprimento() * p.getLargura() * alturaViga))
                .sum();

        return String.format("Volume total de concreto para as vigas baldrames: %.2f m³", volumeTotal);
    }


    @PostMapping("/calculo/tijolos")
    public String calcularTijolosLote(@RequestBody Map<String, Object> payload) {
        List<Integer> idsInt = (List<Integer>) payload.get("ids");
        Double alturaTijolo = Double.parseDouble(payload.get("alturaTijolo").toString());
        Double comprimentoTijolo = Double.parseDouble(payload.get("comprimentoTijolo").toString());

        List<Long> ids = idsInt.stream().map(Integer::longValue).toList();
        List<Parede> paredes = repository.findAllById(ids);

        double areaLiquidaTotal = paredes.stream()
                .mapToDouble(Parede::calcularAreaDesconto)
                .sum();

        double areaUmTijolo = alturaTijolo * comprimentoTijolo;
        long totalTijolos = (long) Math.ceil(areaLiquidaTotal / areaUmTijolo);

        return String.format("Quantidade total de tijolos necessária: %d unidades", totalTijolos);
    }
}


