package com.ghelere.ti.desenvolvimento.repository;

import com.ghelere.ti.desenvolvimento.entity.Alarme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmeRepository extends JpaRepository<Alarme, Long> {
}
