package com.example.CalculadoraObraResidencial.Controller;

import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import com.example.CalculadoraObraResidencial.DTO.ResultadoCalculoDTO;
import com.example.CalculadoraObraResidencial.Service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculos")
@Tag(name = "Cálculos", description = "Relatório completo — executa Etapas 2 e 3 em uma única requisição")
public class CalculoController {

    @Autowired
    private MaterialService materialService;

    @Operation(
        summary     = "Relatório completo (Etapas 2 + 3)",
        description = "Calcula em uma só chamada o volume de concreto (Etapa 2) e a quantidade de tijolos (Etapa 3) para as paredes informadas."
    )
    @PostMapping("/processar")
    public ResponseEntity<ResultadoCalculoDTO> processarCalculo(@Valid @RequestBody ProjetoCalculoDTO projeto) {
        return ResponseEntity.ok(materialService.calcularProjetoCompleto(projeto));
    }
}
