package com.ghelere.ti.desenvolvimento.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ifleet_veiculos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Veiculo {

    @Id
    private String placaVeiculo;

    private String idDispositivo;

    private String idTerminal;

    private String sim;

    private int quantidadeCanais;

    private String nomeCanal;

    private int corPlaca;

    @ManyToOne
    @JoinColumn(name = "frota_informacao_id", nullable = false)
    private FrotaInformacao idGrupo;

    private String tipoDispositivo;

    private String tipoConexao;

    private String usuarioDispositivo;

    private String senhaDispositivo;

    private String ipRegistro;

    private int portaRegistro;

    private String ipTransmissao;

    private int portaTransmissao;

    private int en;
}
