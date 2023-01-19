package com.br.adrianm.messaginggateway.infra.rabbitmq.impl;

import com.br.adrianm.messaginggateway.config.exceptions.ConnectionException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class ConnectionBroker {

    private Connection connection;

    private ConnectionBroker() {

    }

    public void setConnection(String host, String username, String password) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setUsername(username);
            factory.setPassword(password);

            Connection connection = factory.newConnection();

            this.connection = connection;
        } catch(Exception e) {
            throw new ConnectionException("Erro ao abrir a conexao");
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception e) {
            throw new ConnectionException("Erro ao fechar a conexao");
        }
    }

    protected Connection getConnectionInstance() {
        return this.connection;
    }

    protected Channel getChannel() {
        try {
            return connection.createChannel();
        } catch(Exception e) {
            throw new ConnectionException("Erro ao obter o channel");
        }
    }

}
