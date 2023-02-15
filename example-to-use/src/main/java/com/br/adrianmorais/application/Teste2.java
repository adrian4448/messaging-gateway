package com.br.adrianmorais.application;

import com.br.adrianmorais.annotations.MessageEndPoint;

public class Teste2 {

    @MessageEndPoint
    public String teste2(String payload) throws Exception {
        Thread.sleep(30000L);

        return "Teste 2";
    }
}
