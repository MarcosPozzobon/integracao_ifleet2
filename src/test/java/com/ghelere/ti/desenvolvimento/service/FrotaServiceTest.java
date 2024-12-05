package com.ghelere.ti.desenvolvimento.service;

import com.ghelere.ti.desenvolvimento.entity.FrotaInformacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class FrotaServiceTest {

    @Autowired
    FrotaService service;
    private FrotaInformacao frotaExemplo;
    private FrotaInformacao frotaExemplo2;

    @BeforeEach
    void setup() {
        frotaExemplo = new FrotaInformacao();
        frotaExemplo.setGrupoId(35L);
        frotaExemplo.setGrupoPaiId(23L);
        frotaExemplo.setNomeGrupo("EXEMPLO PARA TESTE");

        frotaExemplo2 = new FrotaInformacao();
        frotaExemplo2.setGrupoId(36L);
        frotaExemplo2.setGrupoPaiId(23L);
        frotaExemplo2.setNomeGrupo("EXEMPLO PARA TESTE 2");
    }

    @Test
    @DisplayName("Quando salvar uma frota, a mesma não pode ser nula ou perder a consistencia dos dados")
    void testeSalvarFrotaDeveSalvarUmaFrotaNoBancoDeDados(){
        service.salvarFrota(frotaExemplo);
        FrotaInformacao frotaSalva = service.buscarPorGrupoId(35L); // Supondo que haja um método para buscar por ID
        assertNotNull(frotaSalva, "A frota salva não deve ser nula");
        assertEquals(frotaExemplo.getGrupoId(), frotaSalva.getGrupoId());
        assertEquals(frotaExemplo.getNomeGrupo(), frotaSalva.getNomeGrupo());
        assertEquals(frotaExemplo.getGrupoPaiId(), frotaSalva.getGrupoPaiId());
    }

}
