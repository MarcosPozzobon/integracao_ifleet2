package com.ghelere.ti.desenvolvimento.repository;

import com.ghelere.ti.desenvolvimento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByPlacaVeiculo(String placaVeiculo);

    Veiculo findByPlacaVeiculo(String placaVeiculo);

}
