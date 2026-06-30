package com.example.CalculadoraObraResidencial.Repository;

import com.example.CalculadoraObraResidencial.Entities.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    // Permite buscar os orçamentos pelo nome exato do usuário (Requisito do PDF)
    List<Orcamento> findByNomeUsuarioContainingIgnoreCase(String nomeUsuario);
}
