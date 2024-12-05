package com.ghelere.ti.desenvolvimento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "ifleet_alarmes")
public class Alarme {

    @Id
    @NotNull
    private Long id;
    
    @NotNull
    @Column(name = "descricao_central")
    private String descricaoCentral;

}
