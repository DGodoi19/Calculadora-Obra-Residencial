package com.example.CalculadoraObraResidencial.Controller;

import com.example.CalculadoraObraResidencial.Service.MaterialService;
import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/processar")
    public String processar(@RequestBody ProjetoCalculoDTO projeto) {
        // O Controller não faz conta, ele apenas pede para o Service fazer
        return materialService.calcularProjetoCompleto(projeto);
    }
}
