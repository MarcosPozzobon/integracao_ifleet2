package com.ghelere.ti.desenvolvimento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "ifleet_frota_informacao")
public class FrotaInformacao {

    @Id
    @Column(name = "grupo_id", nullable = false)
    private Long grupoId;

    @Column(name = "nome_grupo", nullable = false)
    private String nomeGrupo;

    @Column(name = "grupo_pai_id", nullable = false)
    private Long grupoPaiId;

}
