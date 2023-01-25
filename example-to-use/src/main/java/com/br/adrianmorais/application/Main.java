package com.br.adrianmorais.application;

import com.br.adrianmorais.annotations.ExecuteMessageEndPoint;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Main {

    private static final String PROJECT_QUEUE = "teste";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("admin");
            factory.setPassword("admin");

            System.setProperty("RABBITMQ_HOST", "localhost");
            System.setProperty("RABBITMQ_USER", "admin");
            System.setProperty("RABBITMQ_PASSWORD", "admin");

            Connection connection = factory.newConnection();

            var channel = connection.createChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    ExecuteMessageEndPoint.executeEndPoint(delivery, TesteClasse.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            channel.basicConsume(PROJECT_QUEUE, deliverCallback, (consumerTag, delivery) -> { });

            do { } while (true);
        } catch (Exception e) {

        }
    }
}