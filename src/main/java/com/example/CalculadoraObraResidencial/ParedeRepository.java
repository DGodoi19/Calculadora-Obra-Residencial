package com.example.CalculadoraObraResidencial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ParedeRepository extends JpaRepository<Parede, Long> {

}
