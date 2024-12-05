package com.ghelere.ti.desenvolvimento.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ghelere.ti.desenvolvimento.entity.AlarmeInformacao;
import com.ghelere.ti.desenvolvimento.repository.AlarmeInformacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmeService {

    private final AlarmeInformacaoRepository alarmeInformacaoRepository;

    public List<AlarmeInformacao> listarAlarmesTipoPerdaDeVideo(){
        return alarmeInformacaoRepository.listByPerdaDeVideo();
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

    public Date getDate(JsonNode node, String fieldName) {
        try {
            String dateString = node.has(fieldName) ? node.get(fieldName).asText() : null;
            if (dateString != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(dateString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void salvarAlarmeInformacao(AlarmeInformacao alarmeInformacao) {
        alarmeInformacaoRepository.save(alarmeInformacao);
    }
}
