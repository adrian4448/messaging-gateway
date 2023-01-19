package com.br.adrianm.messaginggateway.infra.rabbitmq;

public interface MessagePublisher {
    void publishAsyncMessage(String message, String queue, String endpoint);
    Object publishMessage(String message, String queue, String endpoint);
}
