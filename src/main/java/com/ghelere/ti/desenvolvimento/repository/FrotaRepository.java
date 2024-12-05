package com.ghelere.ti.desenvolvimento.repository;

import com.ghelere.ti.desenvolvimento.entity.FrotaInformacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrotaRepository extends JpaRepository<FrotaInformacao, Long> {

    boolean existsByGrupoId(Long groupId);

    FrotaInformacao findByGrupoId(Long grupoId);

}
