package com.message.mqtt;

import com.message.mqtt.service.MqttConnection;
import com.message.mqtt.service.MqttConsumer;

public class ConsumeMain {
    public static final String URL = "tcp://broker.hivemq.com:1883";
    public static final String ALIAS = "public";
    public static final String TOPIC = "sample";

    public static void main(String[] args) {
        // Connection
        new MqttConnection().connect(ALIAS, URL);

        // Consume
        new MqttConsumer().consume(ALIAS, TOPIC);
    }
}
