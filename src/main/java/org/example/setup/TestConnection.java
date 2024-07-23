package org.example.setup;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestConnection {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection()) {
            System.out.println("Connection to RabbitMQ established");
        } catch (Exception e) {
            System.err.println("Failed to connect to RabbitMQ: " + e.getMessage());
        }
    }
}
