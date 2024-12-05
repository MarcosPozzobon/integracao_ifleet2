package com.ghelere.ti.desenvolvimento.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghelere.ti.desenvolvimento.controller.request.AlarmCountRequest;
import com.ghelere.ti.desenvolvimento.entity.Alarme;
import com.ghelere.ti.desenvolvimento.entity.AlarmeInformacao;
import com.ghelere.ti.desenvolvimento.entity.Veiculo;
import com.ghelere.ti.desenvolvimento.service.AlarmeService;
import com.ghelere.ti.desenvolvimento.service.VeiculoService;
import com.ghelere.ti.desenvolvimento.utils.ParamsService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/alertas")
public class AlertaController {

    @Autowired
    private AlarmeService alarmeService;

    @Autowired
    private ParamsService paramsBuilderService;

    @Autowired
    private VeiculoService veiculoService;

    @Value("${ifleet.token}")
    private String IFLEET_TOKEN;

    @Value("${ifleet.server}")
    private String IFLEET_SERVER;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final ConnectionPool connectionPool = new ConnectionPool(10, 5, TimeUnit.MINUTES);
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    @GetMapping("/listar-alarmes-perda-video")
    public ResponseEntity<List<AlarmeInformacao>> listarAlarmes(){  // reotnrar o tipo adequado
        return ResponseEntity.ok(alarmeService.listarAlarmesTipoPerdaDeVideo());
    }

    @PostMapping("/salvar-informacoes-alarmes")
    public ResponseEntity<Void> salvarInformacoesAlarmes(@RequestBody AlarmCountRequest request) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        try {
            List<Veiculo> listaDeVeiculos = veiculoService.obterTodosOsVeiculos();
            List<CompletableFuture<Void>> listaDeFuturos = new ArrayList<>();

            for (Veiculo veiculoAtual : listaDeVeiculos) {
                CompletableFuture<Void> futuro = CompletableFuture.runAsync(() -> {
                    try {
                        String terminalId = veiculoAtual.getIdTerminal();
                        ObjectMapper mapeadorDeObjetos = new ObjectMapper();
                        Map<String, Object> parametros = paramsBuilderService.construirParametros(
                                request.starttime(), request.endtime(), request.type(), IFLEET_TOKEN, List.of(terminalId));

                        String json = mapeadorDeObjetos.writeValueAsString(parametros);
                        okhttp3.RequestBody corpoDaRequisicao = okhttp3.RequestBody.create(json, JSON);
                        Request requisicao = new Request.Builder()
                                .url(IFLEET_SERVER + "/api/v1/basic/alarm/count")
                                .post(corpoDaRequisicao)
                                .addHeader("Content-Type", "application/json")
                                .build();

                        Response resposta = client.newCall(requisicao).execute();
                        if (resposta.isSuccessful()) {
                            ResponseBody corpoDaResposta = resposta.body();
                            if (corpoDaResposta != null) {
                                String respostaString = corpoDaResposta.string();
                                JsonNode noDeResposta = mapeadorDeObjetos.readTree(respostaString);
                                JsonNode arrayDeDados = noDeResposta.get("data");

                                if (arrayDeDados != null && arrayDeDados.isArray()) {
                                    for (JsonNode noAtual : arrayDeDados) {
                                        Alarme tipoDeAlarme = new Alarme();
                                        tipoDeAlarme.setId(request.type());
                                        AlarmeInformacao informacaoDeAlarme = new AlarmeInformacao();
                                        informacaoDeAlarme.setTerminalId(alarmeService.getText(noAtual, "terid"));
                                        informacaoDeAlarme.setDataAtual(alarmeService.getDate(noAtual, "date"));
                                        informacaoDeAlarme.setContagemDeAlarmes(alarmeService.getLong(noAtual, "count"));
                                        informacaoDeAlarme.setTipoAlarme(tipoDeAlarme);

                                        alarmeService.salvarAlarmeInformacao(informacaoDeAlarme);
                                    }
                                }
                            }
                        } else {
                            log.error("Falha na requisição: " + resposta);
                        }
                    } catch (Exception e) {
                       log.error("Ocorreu um erro ao realizar esta ação: " + e.getMessage(), e);
                    }
                }, executor);

                listaDeFuturos.add(futuro);
            }

            CompletableFuture.allOf(listaDeFuturos.toArray(new CompletableFuture[0])).get();

        } catch (InterruptedException | ExecutionException e) {
            log.error("Ocorreu um erro ao realizar esta ação: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
        return ResponseEntity.noContent().build();
    }
}
