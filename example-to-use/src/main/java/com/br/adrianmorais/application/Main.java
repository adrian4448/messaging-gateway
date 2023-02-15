package com.br.adrianmorais.application;

import com.br.adrianmorais.annotations.ExecuteMessageEndPoint;
import com.br.adrianmorais.annotations.infra.rabbitmq.ConnectionProvider;
import com.rabbitmq.client.DeliverCallback;

import java.util.List;

public class Main {

    private static final String PROJECT_QUEUE = "teste";

    public static void main(String[] args) {
        try {
            ConnectionProvider connectionProvider = ConnectionProvider.getConnectionInstance();
            connectionProvider.setConnection("localhost", "admin", "admin");
            var connection = connectionProvider.getConnection();

            var channel = connection.createChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    // Aqui voce pode Colocar todas as classes que estiverem anotadas com o @MessageEndPoint
                    ExecuteMessageEndPoint.executeEndPoint(delivery, List.of(TesteClasse.class, Teste2.class));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            channel.basicConsume(PROJECT_QUEUE, true, deliverCallback, (consumerTag, delivery) -> { });

            do { } while (true);
        } catch (Exception e) {

        }
    }
}