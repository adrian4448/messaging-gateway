package com.br.adrianmorais.annotations.infra.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.HashMap;

public class MessageHandler {

    private ConnectionProvider connectionProvider = ConnectionProvider.getConnectionInstance();

    public void sendCallbackMessage(Delivery delivery, String endpoint, String returnedValue) throws Exception {
        var messageId = delivery.getProperties().getHeaders().get("MESSAGE_ID").toString();
        var queue = delivery.getProperties().getReplyTo();

        var con = connectionProvider.getConnection();
        var channel = con.createChannel();

        var headers = new HashMap<String, Object>();

        headers.put("END_POINT", endpoint);
        headers.put("MESSAGE_ID", messageId);

        AMQP.BasicProperties props = new AMQP.BasicProperties()
                .builder()
                .headers(headers)
                .build();

        channel.basicPublish("", queue, props, returnedValue.toString().getBytes());
        channel.close();
    }

}
