package com.br.adrianmorais.application;

import com.br.adrianmorais.annotations.MessageEndPoint;

public class TesteClasse {
    @MessageEndPoint
    public String teste(String payload) throws InterruptedException {
        return payload;
    }
}
