package com.example.CalculadoraObraResidencial.Service;

import com.example.CalculadoraObraResidencial.DTO.*;
import com.example.CalculadoraObraResidencial.Entities.Buraco;
import com.example.CalculadoraObraResidencial.Entities.Parede;
import com.example.CalculadoraObraResidencial.Exception.RecursoNaoEncontradoException;
import com.example.CalculadoraObraResidencial.Repository.ParedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParedeService {

    @Autowired
    private ParedeRepository repository;

    public ParedeResponseDTO criar(ParedeRequestDTO dto) {
        Parede parede = toEntity(dto);
        return toResponse(repository.save(parede));
    }

    public List<ParedeResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public String calcularVolumeConcreto(ConcretoRequestDTO dto) {
        List<Parede> paredes = repository.findAllById(dto.getIds());
        if (paredes.isEmpty()) throw new RecursoNaoEncontradoException("Nenhuma parede encontrada para os IDs informados.");

        double volumeTotal = paredes.stream()
                .mapToDouble(p -> p.getComprimento() * p.getLargura() * dto.getAlturaViga())
                .sum();

        return String.format("Volume total de concreto para as vigas baldrames: %.2f m³", volumeTotal);
    }

    public String calcularTijolos(TijoloRequestDTO dto) {
        List<Parede> paredes = repository.findAllById(dto.getIds());
        if (paredes.isEmpty()) throw new RecursoNaoEncontradoException("Nenhuma parede encontrada para os IDs informados.");

        double areaLiquidaTotal = paredes.stream()
                .mapToDouble(Parede::calcularAreaLiquida)
                .sum();

        double areaUmTijolo = dto.getAlturaTijolo() * dto.getComprimentoTijolo();
        long totalTijolos = (long) Math.ceil(areaLiquidaTotal / areaUmTijolo);

        return String.format("Quantidade total de tijolos necessária: %d unidades", totalTijolos);
    }

    private Parede toEntity(ParedeRequestDTO dto) {
        Parede p = new Parede();
        p.setComprimento(dto.getComprimento());
        p.setLargura(dto.getLargura());
        p.setAltura(dto.getAltura());
        if (dto.getPorta() != null)
            p.setPorta(new Buraco(dto.getPorta().getLargura(), dto.getPorta().getAltura()));
        if (dto.getJanela() != null)
            p.setJanela(new Buraco(dto.getJanela().getLargura(), dto.getJanela().getAltura()));
        return p;
    }

    private ParedeResponseDTO toResponse(Parede p) {
        ParedeResponseDTO r = new ParedeResponseDTO();
        r.setId(p.getId());
        r.setComprimento(p.getComprimento());
        r.setLargura(p.getLargura());
        r.setAltura(p.getAltura());
        r.setAreaLiquida(p.calcularAreaLiquida());
        r.setVolumeConcreto(p.calcularVolumeConcreto());
        if (p.getPorta() != null)
            r.setPorta(new BuracoDTO(p.getPorta().getLargura(), p.getPorta().getAltura()));
        if (p.getJanela() != null)
            r.setJanela(new BuracoDTO(p.getJanela().getLargura(), p.getJanela().getAltura()));
        return r;
    }
}
