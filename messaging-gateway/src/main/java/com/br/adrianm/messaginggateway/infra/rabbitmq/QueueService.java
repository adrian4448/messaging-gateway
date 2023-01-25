package com.br.adrianm.messaginggateway.infra.rabbitmq;

public interface QueueService {
    void createQueues(String queueName);
    void deleteQueues(String queueName);
}
