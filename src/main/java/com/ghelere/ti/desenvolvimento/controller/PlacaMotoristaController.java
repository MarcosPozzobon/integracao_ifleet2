package com.ghelere.ti.desenvolvimento.controller;

import com.ghelere.ti.desenvolvimento.entity.PlacaMotorista;
import com.ghelere.ti.desenvolvimento.entity.Veiculo;
import com.ghelere.ti.desenvolvimento.service.PlacaMotoristaService;
import com.ghelere.ti.desenvolvimento.service.VeiculoService;
import com.ghelere.ti.desenvolvimento.utils.CsvReaderService;
import com.ghelere.ti.desenvolvimento.utils.MercosulParserService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/informacoes-motorista")
@RequiredArgsConstructor
public class PlacaMotoristaController {

    private final CsvReaderService csvReaderService;

    private final VeiculoService veiculoService;

    private final MercosulParserService mercosulParserService;

    private final PlacaMotoristaService placaMotoristaService;

    @GetMapping("/obter-relacao")
    public ResponseEntity<List<PlacaMotorista>> obterRelacaoPlacaXMotorista() {
        return ResponseEntity.ok(placaMotoristaService.encontrarTodos());
    }

    @GetMapping("/obter-veiculo")
    public ResponseEntity<?> obterVeiculoPorPlaca(@RequestParam("placa") String placa) {
        if (placa == null || placa.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A placa fornecida está vazia ou nula.");
        }
        if(!veiculoService.existePlacaNoBanco(placa)){
            String placaConvertida = mercosulParserService.converterParaMercosul(placa);
            return ResponseEntity.ok(veiculoService.encontrarVeiculoPorPlaca(placaConvertida));
        }
        return ResponseEntity.ok(veiculoService.encontrarVeiculoPorPlaca(placa));
    }

    @PostMapping("/salvar-relacao-placa-motorista")
    public ResponseEntity<Void> salvarRelacao(@RequestParam(name = "file") MultipartFile file){
        List<PlacaMotorista> relacao = csvReaderService.loader(file);
        placaMotoristaService.salvarRelacaoPlacaXMotorista(relacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
