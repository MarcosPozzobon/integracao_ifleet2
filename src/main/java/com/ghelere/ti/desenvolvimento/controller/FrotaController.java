package com.ghelere.ti.desenvolvimento.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghelere.ti.desenvolvimento.entity.FrotaInformacao;
import com.ghelere.ti.desenvolvimento.service.FrotaService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/frota")
public class FrotaController {

    @Value("${ifleet.token}")
    private String IFLEET_TOKEN;

    @Value("${ifleet.username}")
    private String IFLEET_USERNAME;

    @Value("${ifleet.password}")
    private String IFLEET_PASSWORD;

    @Value("${ifleet.server}")
    private String IFLEET_SERVER;

    @Autowired
    FrotaService service;

    @PostMapping("/salvar-informacoes-request-no-banco-de-dados")
    public ResponseEntity<Void> salvarGruposFrotas() {
        try {
            String endpoint = IFLEET_SERVER + "/api/v1/basic/groups?key=" + IFLEET_TOKEN;
            OkHttpClient clientHttp = new OkHttpClient();
            Request requisicao = new Request.Builder()
                    .url(endpoint)
                    .build();
            Response respostaServidor = clientHttp.newCall(requisicao).execute();
            ResponseBody corpoRespostaServidor = respostaServidor.body();

            if (corpoRespostaServidor != null && respostaServidor.isSuccessful()) {
                String resposta = corpoRespostaServidor.string(); // aqui ele já traz os resultados da API
                ObjectMapper mapper = new ObjectMapper();
                JsonNode nodes = mapper.readTree(resposta);
                List<FrotaInformacao> frotaInformacoes = new ArrayList<>();
                // Iterando sobre o array "data" do JSON q contem as informacoes q precisamos
                JsonNode dataArray = nodes.get("data");
                if (dataArray != null && dataArray.isArray()) {
                    for (JsonNode jsonNode : dataArray) {
                        FrotaInformacao frotaInformacao = new FrotaInformacao();
                        frotaInformacao.setGrupoId(jsonNode.get("groupid").asLong());
                        frotaInformacao.setNomeGrupo(jsonNode.get("groupname").asText());
                        frotaInformacao.setGrupoPaiId(jsonNode.get("groupfatherid").asLong());
                        frotaInformacoes.add(frotaInformacao);
                    }
                }
                service.salvarListaFrotas(frotaInformacoes);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
