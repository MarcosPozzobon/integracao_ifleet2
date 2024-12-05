package com.ghelere.ti.desenvolvimento.service;

import com.ghelere.ti.desenvolvimento.entity.PlacaMotorista;
import com.ghelere.ti.desenvolvimento.repository.PlacaMotoristaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlacaMotoristaService {

    private final PlacaMotoristaRepository repository;

    public void salvarRelacaoPlacaXMotorista(List<PlacaMotorista> relacao){
        for(PlacaMotorista itemAtual : relacao){
            repository.save(itemAtual);
        }
    }

    public void salvarObjeto(PlacaMotorista objeto){
        repository.save(objeto);
    }

    public List<PlacaMotorista> encontrarTodos(){
       return repository.findAll();
    }
}
