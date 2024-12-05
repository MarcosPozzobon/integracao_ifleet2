package com.ghelere.ti.desenvolvimento.utils;

import com.ghelere.ti.desenvolvimento.entity.PlacaMotorista;
import com.ghelere.ti.desenvolvimento.entity.Veiculo;
import com.ghelere.ti.desenvolvimento.repository.VeiculoRepository;
import com.ghelere.ti.desenvolvimento.service.PlacaMotoristaService;
import com.ghelere.ti.desenvolvimento.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvReaderService {

    private final VeiculoService service;

    private final PlacaMotoristaService placaMotoristaService;

    private final VeiculoRepository repository;

    private final MercosulParserService mercosulParserService;

    public List<PlacaMotorista> loader(MultipartFile file) {
        List<PlacaMotorista> relacaoFinal = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             Scanner scanner = new Scanner(inputStream)
        ) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String[] data = scanner.nextLine().split(";"); // aqui ele já tem todos os dados do CSV
                String placaCsv = data[0];
                String motorista = data[1];
                String ultimaData = data[2];

                if (motorista != null && placaCsv != null  && !service.existePlacaNoBanco(placaCsv)) {
                    String placaConvertida = mercosulParserService.converterParaMercosul(placaCsv);

                    if(!service.existePlacaNoBanco(placaConvertida)) {
                        log.warn("Mesmo convertendo a placa ainda sim não foi possível encontrar ela na relação vinda do retorno da API do Ifleet! Horario da operacao: " + LocalDateTime.now());
                    } else {
                        Veiculo veiculoExistente = service.encontrarVeiculoPorPlaca(placaConvertida);

                        PlacaMotorista placaMotorista = new PlacaMotorista();
                        placaMotorista.setVeiculo(veiculoExistente);
                        placaMotorista.setMotorista(motorista);
                        placaMotorista.setUltimaDataString(ultimaData);

                        relacaoFinal.add(placaMotorista);
                    }
                } else {
                    Veiculo veiculoExistente = service.encontrarVeiculoPorPlaca(placaCsv);

                    PlacaMotorista placaMotorista = new PlacaMotorista();
                    placaMotorista.setVeiculo(veiculoExistente);
                    placaMotorista.setMotorista(motorista);
                    placaMotorista.setUltimaDataString(ultimaData);

                    relacaoFinal.add(placaMotorista);
                }
            }
        } catch (Exception e) {
            log.error("Erro inesperado: " + e.getMessage());
        }
        return relacaoFinal;
    }
}



