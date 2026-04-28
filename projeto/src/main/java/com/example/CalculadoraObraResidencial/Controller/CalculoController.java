package com.example.CalculadoraObraResidencial.Controller;

import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import com.example.CalculadoraObraResidencial.DTO.ResultadoCalculoDTO;
import com.example.CalculadoraObraResidencial.Service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {

    @Autowired
    private MaterialService materialService;


    @PostMapping("/processar")
    public ResponseEntity<ResultadoCalculoDTO> processarCalculo(@Valid @RequestBody ProjetoCalculoDTO projeto) {
        return ResponseEntity.ok(materialService.calcularProjetoCompleto(projeto));
    }
}
