package com.br.adrianm.messaginggateway.infra.rabbitmq.impl;

import com.br.adrianm.messaginggateway.config.exceptions.SendMessageException;
import com.br.adrianm.messaginggateway.infra.rabbitmq.MessagePublisher;
import com.br.adrianm.messaginggateway.infra.rabbitmq.pojo.MessageReturnContext;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class MessagePublisherImpl implements MessagePublisher {
    @Autowired
    private ConnectionBroker connection;

    public void publishAsyncMessage(String message, String queue, String endpoint) {
        try {
            var channel = connection.getChannel();
            var headers = new HashMap<String, Object>();

            headers.put("END_POINT", endpoint);
            headers.put("ASYNC", true);

            BasicProperties basicProperties = new BasicProperties()
                    .builder()
                    .headers(headers)
                    .build();

            channel.basicPublish("", queue, basicProperties, message.getBytes());

            channel.close();
        } catch(Exception e) {
            throw new SendMessageException("Ocorreu um erro ao enviar a mensagem");
        }
    }

    @Override
    public Object publishMessage(String message, String queue, String endpoint) {
        var context = new MessageReturnContext();

        try {
            var callbackQueueName = queue + "_callback";
            var channel = connection.getChannel();
            var headers = new HashMap<String, Object>();
            var messageId = UUID.randomUUID().toString();

            headers.put("END_POINT", endpoint);
            headers.put("ASYNC", true);
            headers.put("MESSAGE_ID", messageId);

            BasicProperties props = new BasicProperties()
                    .builder()
                    .headers(headers)
                    .replyTo(callbackQueueName)
                    .build();

            channel.basicPublish("", queue, props, message.getBytes());

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String messageReceived = new String(delivery.getBody(), "UTF-8");

                if (delivery.getProperties().getHeaders().get("END_POINT").toString().equals(endpoint) &&
                        delivery.getProperties().getHeaders().get("MESSAGE_ID").toString().equals(messageId)) {
                    context.setResponseJson(messageReceived);

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };

            boolean autoAck = true;

            channel.basicConsume(callbackQueueName, autoAck, deliverCallback, (consumerTag, delivery) -> { });

            do { } while (context.getResponseJson() == null);

            return context.getResponseJson();
        } catch (Exception e) {
            throw new SendMessageException("Ocorreu um erro ao enviar a mensagem");
        }
    }
}
