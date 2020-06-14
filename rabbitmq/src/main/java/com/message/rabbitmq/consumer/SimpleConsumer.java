package com.message.rabbitmq.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumer extends AbstractRabbiMqConsumer {

    @Value("${rabbitmq.host:127.0.0.1}")
    private String host;

    @Value("${rabbitmq.port:5672}")
    private Integer port;

    @Value("${rabbitmq.userName:test_user}")
    private String userName;

    @Value("${rabbitmq.password:1234}")
    private String password;

    @Value("${rabbitmq.virtualHost:test}")
    private String virtualHost;

    @Value("${rabbitmq.queue:simple_queue}")
    private String queue;

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getVirtualHost() {
        return virtualHost;
    }

    @Override
    public String getQueue() {
        return queue;
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}