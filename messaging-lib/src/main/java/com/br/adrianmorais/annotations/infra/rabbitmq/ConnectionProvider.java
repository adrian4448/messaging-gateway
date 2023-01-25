package com.br.adrianmorais.annotations.infra.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionProvider {

    private Connection connection;
    private static ConnectionProvider instance;

    private ConnectionProvider() {

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
            throw new IllegalArgumentException("Erro ao abrir a conexao");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConnectionProvider getConnectionInstance() {
        if (instance == null) {
            instance = new ConnectionProvider();
        }
        return instance;
    }
}
