package com.message.mqtt.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MqttConnection {

    private static Map<String, MqttClient> CONNECTION_MAP = new ConcurrentHashMap<>();

    /**
     * MQTT Client 가져오기 실행.
     *
     * @param
     * @return
     */
    public MqttClient getMqttClient(String alias) {
        MqttClient mqttClient = CONNECTION_MAP.get(alias);
        if (Objects.isNull(mqttClient) || !mqttClient.isConnected())
            return null;

        return mqttClient;
    }

    /**
     * MQTT Connection
     *
     * @param
     */
    public MqttClient connect(String url) {
        return this.connect(url, url);
    }

    public MqttClient connect(String alias, String url) {
        return this.connect(alias, url, null, null);
    }

    public MqttClient connect(String alias, String url, String userName, String password) {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        if (Objects.nonNull(userName) && Objects.nonNull(password)) {
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
        }

        try {
            String clientId = UUID.randomUUID().toString();
            MqttClient mqttClient = new MqttClient(url, clientId, new MemoryPersistence());
            mqttClient.connect(connOpts);

            CONNECTION_MAP.put(alias, mqttClient);

            return mqttClient;
        } catch (MqttException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Disconnect MQTT Client
     *
     * @param
     */
    public boolean disconnect(String alias) {
        MqttClient mqttClient = CONNECTION_MAP.get(alias);
        if (Objects.nonNull(mqttClient)) {
            CONNECTION_MAP.remove(alias);
            this.disconnect(mqttClient);
        }

        return true;
    }

    public boolean disconnect(MqttClient mqttClient) {
        if (Objects.isNull(mqttClient) || !mqttClient.isConnected())
            return true;

        try {
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException e) {
            return false;
        }

        return true;
    }

    public boolean disconnectAll() {
        Set<String> keys = CONNECTION_MAP.keySet();
        for (String key : keys)
            this.disconnect(key);

        return true;
    }

    public boolean isConnected(String alias) {
        try {
            return Objects.nonNull(this.getMqttClient(alias)) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
