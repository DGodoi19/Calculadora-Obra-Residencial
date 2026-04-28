package com.example.CalculadoraObraResidencial.Repository;

import com.example.CalculadoraObraResidencial.Entities.Parede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParedeRepository extends JpaRepository<Parede, Long> {
}
