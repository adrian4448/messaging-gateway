package com.br.adrianm.messaginggateway.infra.rabbitmq.impl;

import com.br.adrianm.messaginggateway.infra.rabbitmq.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueServiceImpl implements QueueService {
    @Autowired
    private ConnectionBroker connectionBroker;

    @Override
    public void createQueues(String queueName) {
        try {
            var channel = connectionBroker.getChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueDeclare(queueName + "_callback", false, false, false, null);
        } catch (Exception e) {

        }
    }

    @Override
    public void deleteQueues(String queueName) {
        try {
            var channel = connectionBroker.getChannel();

            channel.queueDelete(queueName);
            channel.queueDelete(queueName + "_callback");
        } catch (Exception e) {

        }
    }
}
