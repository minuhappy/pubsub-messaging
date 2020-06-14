package com.message.mqtt;

import java.util.Scanner;

import com.message.mqtt.service.MqttConnection;
import com.message.mqtt.service.MqttPublisher;

public class PublishMain {
    public static final String URL = "tcp://broker.hivemq.com:1883";
    public static final String ALIAS = "public";
    public static final String TOPIC = "sample";

    public static void main(String[] args) {
        // Connection
        new MqttConnection().connect(ALIAS, URL);

        // Publish
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Send Message : ");
            String message = scanner.nextLine();

            if (message.equals("exit")) {
                System.out.println("Terminate.");
                break;
            }

            System.out.println("전송중...");
            new MqttPublisher().send(ALIAS, TOPIC, message);
        }
    }
}
