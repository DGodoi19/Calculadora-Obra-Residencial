package com.example.CalculadoraObraResidencial.Controller;

import com.example.CalculadoraObraResidencial.DTO.*;
import com.example.CalculadoraObraResidencial.Service.ParedeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paredes")
@Tag(name = "Paredes", description = "Etapa 1 — Cadastro de paredes (arestas do grafo G=(V,A)) | Etapa 2 — Concreto | Etapa 3 — Tijolos")
public class ParedeController {

    @Autowired
    private ParedeService paredeService;

    @Operation(
        summary     = "Etapa 1 — Criar parede",
        description = "Cadastra uma nova parede com comprimento, largura, altura e opcionalmente porta e janela."
    )
    @PostMapping
    public ResponseEntity<ParedeResponseDTO> criarParede(@Valid @RequestBody ParedeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paredeService.criar(dto));
    }

    @Operation(
        summary     = "Etapa 1 — Listar paredes",
        description = "Retorna todas as paredes cadastradas. Use os IDs retornados nas etapas 2 e 3."
    )
    @GetMapping
    public ResponseEntity<List<ParedeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(paredeService.listarTodas());
    }

    @Operation(
        summary     = "Etapa 2 — Calcular volume de concreto (viga baldrame)",
        description = "Recebe uma lista de IDs de paredes e a altura da viga. Fórmula: comprimento × largura × alturaViga."
    )
    @PostMapping("/calculo/concreto")
    public ResponseEntity<String> calcularVolumeConcreto(@Valid @RequestBody ConcretoRequestDTO dto) {
        return ResponseEntity.ok(paredeService.calcularVolumeConcreto(dto));
    }

    @Operation(
        summary     = "Etapa 3 — Calcular quantidade de tijolos",
        description = "Recebe uma lista de IDs de paredes e as dimensões do tijolo. Desconta automaticamente vãos de porta e janela."
    )
    @PostMapping("/calculo/tijolos")
    public ResponseEntity<String> calcularTijolos(@Valid @RequestBody TijoloRequestDTO dto) {
        return ResponseEntity.ok(paredeService.calcularTijolos(dto));
    }
}
