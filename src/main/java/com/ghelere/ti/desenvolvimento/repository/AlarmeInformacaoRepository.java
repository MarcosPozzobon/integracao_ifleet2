package com.ghelere.ti.desenvolvimento.repository;

import com.ghelere.ti.desenvolvimento.entity.AlarmeInformacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlarmeInformacaoRepository extends JpaRepository<AlarmeInformacao, Long> {

    @Query(value = "SELECT * FROM alarme_informacao WHERE alarme_tipo_id = 1", nativeQuery = true)
    List<AlarmeInformacao> listByPerdaDeVideo();

    @Query(value = "SELECT * FROM alarme_informacao WHERE alarme_tipo_id = 2", nativeQuery = true)
    List<AlarmeInformacao> listByDeteccaoDeMovimento();

}
