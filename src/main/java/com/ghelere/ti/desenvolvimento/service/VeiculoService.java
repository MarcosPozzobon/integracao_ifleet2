package com.ghelere.ti.desenvolvimento.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ghelere.ti.desenvolvimento.entity.FrotaInformacao;
import com.ghelere.ti.desenvolvimento.entity.PlacaMotorista;
import com.ghelere.ti.desenvolvimento.entity.Veiculo;
import com.ghelere.ti.desenvolvimento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VeiculoService {

    private final VeiculoRepository repository;

    public List<Veiculo> obterTodosOsVeiculos(){
        return repository.findAll();
    }

    @Transactional
    public void salvarListaVeiculos(List<Veiculo> veiculos) {
        List<Veiculo> novasFrotas = veiculos.stream()
                .filter(frota -> !repository.existsByPlacaVeiculo(frota.getPlacaVeiculo())).collect(Collectors.toList());
        repository.saveAll(veiculos);
    }

    public boolean existePlacaNoBanco(String placa){
        return repository.existsByPlacaVeiculo(placa);
    }


    @Transactional
    public void salvarVeiculo(Veiculo veiculo){
        repository.save(veiculo);
    }

    public Veiculo encontrarVeiculoPorPlaca(String placa){
        return repository.findByPlacaVeiculo(placa);
    }

    public String getText(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asText() : null;
    }

    public Integer getInt(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asInt() : null;
    }

    public Long getLong(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asLong() : null;
    }


}
