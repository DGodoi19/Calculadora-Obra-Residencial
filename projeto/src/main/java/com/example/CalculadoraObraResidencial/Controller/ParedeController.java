package com.example.CalculadoraObraResidencial.Controller;

import com.example.CalculadoraObraResidencial.DTO.*;
import com.example.CalculadoraObraResidencial.Service.ParedeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paredes")
public class ParedeController {

    @Autowired
    private ParedeService paredeService;


    @PostMapping
    public ResponseEntity<ParedeResponseDTO> criarParede(@Valid @RequestBody ParedeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paredeService.criar(dto));
    }


    @GetMapping
    public ResponseEntity<List<ParedeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(paredeService.listarTodas());
    }


    @PostMapping("/calculo/concreto")
    public ResponseEntity<String> calcularVolumeConcreto(@Valid @RequestBody ConcretoRequestDTO dto) {
        return ResponseEntity.ok(paredeService.calcularVolumeConcreto(dto));
    }

    @PostMapping("/calculo/tijolos")
    public ResponseEntity<String> calcularTijolos(@Valid @RequestBody TijoloRequestDTO dto) {
        return ResponseEntity.ok(paredeService.calcularTijolos(dto));
    }
}
