package com.message.mqtt.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConsumer implements MqttCallback {
    /**
     * MQTT Consume
     *
     * @param alias
     * @param topic
     */
    public void consume(String alias, String topic) {
        try {
            MqttClient mqttClient = new MqttConnection().getMqttClient(alias);
            mqttClient.subscribe(topic);
            mqttClient.setCallback(this);
        } catch (MqttException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println("Receive : ".concat(message.toString()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
