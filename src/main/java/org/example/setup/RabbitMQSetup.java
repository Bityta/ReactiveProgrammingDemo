package org.example.setup;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSetup {

    private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    private static final String[] QUEUE_NAMES = {
            "purchase_orders",
            "vehicle_queue"
    };

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            for (String queueName : QUEUE_NAMES) {
                channel.queueDeclare(queueName, true, false, false, null);
                System.out.println("Queue created: " + queueName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
