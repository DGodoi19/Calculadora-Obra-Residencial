package com.example.CalculadoraObraResidencial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{id}/calculos")
    public String verCalculos(@PathVariable Long id) {
        Parede p = repository.findById(id).orElseThrow();

        return String.format(
                "Relatório da Parede %d:\n" +
                        "Volume de Concreto: %.2f m³\n" +
                        "Área Líquida (com descontos): %.2f m²",
                p.getID(), p.calcularVolumeConcreto(), p.calcularAreaDesconto()
        );
    }
}


