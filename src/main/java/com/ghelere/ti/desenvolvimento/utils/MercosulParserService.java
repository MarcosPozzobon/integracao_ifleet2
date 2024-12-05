package com.ghelere.ti.desenvolvimento.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MercosulParserService {

    private static final Map<Character, Character> numeroParaLetra = new HashMap<>();

    static {
        numeroParaLetra.put('0', 'A');
        numeroParaLetra.put('1', 'B');
        numeroParaLetra.put('2', 'C');
        numeroParaLetra.put('3', 'D');
        numeroParaLetra.put('4', 'E');
        numeroParaLetra.put('5', 'F');
        numeroParaLetra.put('6', 'G');
        numeroParaLetra.put('7', 'H');
        numeroParaLetra.put('8', 'I');
        numeroParaLetra.put('9', 'J');
    }

    public String converterParaMercosul(String placaAntiga) throws IllegalArgumentException {
        if (placaAntiga == null || placaAntiga.length() != 7) {
            throw new IllegalArgumentException("Placa antiga deve ter 7 caracteres no formato ABC1234.");
        }

        StringBuilder placaMercosul = new StringBuilder();
        String letras = placaAntiga.substring(0, 4);
        char numeroParaConverter = placaAntiga.charAt(4);
        String numerosRestantes = placaAntiga.substring(5);

        if (Character.isDigit(numeroParaConverter)) {
            char letraConvertida = numeroParaLetra.get(numeroParaConverter);
            placaMercosul.append(letras);
            placaMercosul.append(letraConvertida);
            placaMercosul.append(numerosRestantes);
        }

        return placaMercosul.toString();
    }
}
