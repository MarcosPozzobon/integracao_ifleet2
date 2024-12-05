package com.ghelere.ti.desenvolvimento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ifleet_alarme_informacao")
public class AlarmeInformacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_terminal")
    private String terminalId;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "data_atual")
    private Date dataAtual;

    @Column(name = "contagem_alarmes")
    private Long contagemDeAlarmes;

    @ManyToOne
    @JoinColumn(name = "alarme_tipo_id")
    private Alarme tipoAlarme;
}
