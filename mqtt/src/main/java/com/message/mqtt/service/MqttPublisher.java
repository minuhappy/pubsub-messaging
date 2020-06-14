package com.message.mqtt.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublisher {
    /**
     * Send MQTT Message
     *
     * @param alias
     * @param topic
     * @param data
     */
    public void send(String alias, String topic, String data) {
        MqttClient mqttClient = new MqttConnection().getMqttClient(alias);
        if (mqttClient == null)
            throw new RuntimeException("MQTT Client does not available.");

        MqttMessage message = new MqttMessage(data.getBytes());
        message.setQos(2);

        try {
            mqttClient.publish(topic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
