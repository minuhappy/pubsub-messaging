package com.message.rabbitmq.consumer;

import java.util.Objects;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.rabbitmq.connection.RabbitMqConnection;
import com.rabbitmq.client.Channel;

public abstract class AbstractRabbiMqConsumer {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private Channel channel;

    private boolean isRunning;

    public abstract String getHost();

    public abstract Integer getPort();

    public abstract String getQueue();

    public abstract String getUserName();

    public abstract String getPassword();

    public abstract String getVirtualHost();

    public void run() {
        if (this.isRunning || Objects.isNull(this.getQueue())) {
            return;
        }

        this.isRunning = true;

        while (true) {
            try {
                if (Objects.isNull(channel) || !channel.isOpen()) {
                    this.consume(this.getQueue(), this::receiveMessage);
                    log.info("RabbitMQ Connected.");
                }
            } catch (IllegalArgumentException iae) {
                break;
            } catch (Exception e) {
                log.warn(e.getMessage());
            }

            try {
                long healthCheckTimeMillis = 10000;
                Thread.sleep(healthCheckTimeMillis);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void consume(String queue, Consumer<String> callback) throws Exception {
        this.channel = new RabbitMqConnection()
            .setHost(this.getHost())
            .setPort(this.getPort())
            .setUserName(this.getUserName())
            .setPassword(this.getPassword())
            .setVirtualHost(this.getVirtualHost())
            .getChannel(queue, callback);
    }

    public abstract void receiveMessage(String message);
}