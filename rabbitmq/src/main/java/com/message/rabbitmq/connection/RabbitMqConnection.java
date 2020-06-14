package com.message.rabbitmq.connection;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMqConnection {

    private String host;
    private Integer port;
    private String userName;
    private String password;
    private String virtualHost;

    public Channel getChannel(String queue, Consumer<String> callback) throws Exception {
        Connection connection = this.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                callback.accept(new String(body, StandardCharsets.UTF_8));
            }
        };

        channel.basicConsume(queue, true, consumer);

        return channel;
    }

    private Connection getConnection() throws Exception {
        Objects.requireNonNull(this.host);
        Objects.requireNonNull(this.host);
        Objects.requireNonNull(this.userName);
        Objects.requireNonNull(this.password);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setUsername(this.userName);
        factory.setPassword(this.password);

        Optional.ofNullable(this.port).ifPresent(factory::setPort);
        Optional.ofNullable(this.virtualHost).ifPresent(factory::setVirtualHost);

        return factory.newConnection();
    }

    public String getHost() {
        return host;
    }

    public RabbitMqConnection setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public RabbitMqConnection setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RabbitMqConnection setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RabbitMqConnection setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public RabbitMqConnection setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
        return this;
    }
}