package com.ghelere.ti.desenvolvimento.service;

import com.ghelere.ti.desenvolvimento.entity.FrotaInformacao;
import com.ghelere.ti.desenvolvimento.repository.FrotaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FrotaService {

    private final FrotaRepository repository;

    public void salvarListaFrotas(List<FrotaInformacao> frotaInformacoes) {
        List<FrotaInformacao> novasFrotas = frotaInformacoes.stream()
                .filter(frota -> !repository.existsByGrupoId(frota.getGrupoId()))
                .collect(Collectors.toList());
        repository.saveAll(novasFrotas);
    }

    @Transactional
    public void salvarFrota(FrotaInformacao frota){
        repository.save(frota);
    }

    public FrotaInformacao buscarPorGrupoId(Long grupoId){
        return repository.findByGrupoId(grupoId);
    }

    public List<FrotaInformacao> encontrarTodasAsFrotas(){
        return repository.findAll();
    }
}
