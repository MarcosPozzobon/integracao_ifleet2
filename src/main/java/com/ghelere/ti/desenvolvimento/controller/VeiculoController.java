package com.ghelere.ti.desenvolvimento.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghelere.ti.desenvolvimento.entity.FrotaInformacao;
import com.ghelere.ti.desenvolvimento.entity.Veiculo;
import com.ghelere.ti.desenvolvimento.service.VeiculoService;
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
@RequestMapping("api/v1/veiculo")
public class VeiculoController {

    @Value("${ifleet.token}")
    private String IFLEET_TOKEN;

    @Value("${ifleet.username}")
    private String IFLEET_USERNAME;

    @Value("${ifleet.password}")
    private String IFLEET_PASSWORD;

    @Value("${ifleet.server}")
    private String IFLEET_SERVER;

    @Autowired
    VeiculoService service;


    @PostMapping("/salvar-informacoes-request-no-banco-de-dados")
    public ResponseEntity<Void> salvarListaDispositivos() {
        try {
            String endpoint = IFLEET_SERVER + "/api/v1/basic/devices?key=" + IFLEET_TOKEN;
            OkHttpClient client = new OkHttpClient();
            Request requisicao = new Request.Builder()
                    .url(endpoint)
                    .build();
            Response response = client.newCall(requisicao).execute();
            ResponseBody responseBody = response.body();

            if (responseBody != null && response.isSuccessful()) {
                String resposta = responseBody.string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode nodes = objectMapper.readTree(resposta);
                List<Veiculo> veiculos = new ArrayList<>();
                JsonNode dataArray = nodes.get("data");

                if (dataArray != null && dataArray.isArray()) {
                    for (JsonNode node : dataArray) {
                        Veiculo veiculoASerSalvo = new Veiculo();

                        veiculoASerSalvo.setPlacaVeiculo(service.getText(node, "carlicence"));
                        veiculoASerSalvo.setIdDispositivo(service.getText(node, "deviceid"));
                        veiculoASerSalvo.setIdTerminal(service.getText(node, "terid"));
                        veiculoASerSalvo.setSim(service.getText(node, "sim"));
                        veiculoASerSalvo.setQuantidadeCanais(service.getInt(node, "channelcount"));
                        veiculoASerSalvo.setNomeCanal(service.getText(node, "cname"));
                        veiculoASerSalvo.setCorPlaca(service.getInt(node, "platecolor"));
                        // Configurar objeto FrotaInformacao se "groupid" estiver presente
                        if (node.has("groupid")) {
                            FrotaInformacao grupo = new FrotaInformacao();
                            grupo.setGrupoId(node.get("groupid").asLong());
                            veiculoASerSalvo.setIdGrupo(grupo);
                        }
                        veiculoASerSalvo.setTipoDispositivo(service.getText(node, "devicetype"));
                        veiculoASerSalvo.setTipoConexao(service.getText(node, "linktype"));
                        veiculoASerSalvo.setUsuarioDispositivo(service.getText(node, "deviceusername"));
                        veiculoASerSalvo.setSenhaDispositivo(service.getText(node, "devicepassword"));
                        veiculoASerSalvo.setIpRegistro(service.getText(node, "registerip"));
                        veiculoASerSalvo.setPortaRegistro(service.getInt(node, "registerport"));
                        veiculoASerSalvo.setIpTransmissao(service.getText(node, "transmitip"));
                        veiculoASerSalvo.setPortaTransmissao(service.getInt(node, "transmitport"));
                        veiculoASerSalvo.setEn(service.getInt(node, "en"));

                        veiculos.add(veiculoASerSalvo);
                    }
                    service.salvarListaVeiculos(veiculos);
                }
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao realizar esta ação: " + e.getMessage() + " na classe: " + this.getClass().getName(), e);
        }
    }
}


