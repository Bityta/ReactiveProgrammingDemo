package org.example.config;

import com.rabbitmq.client.ConnectionFactory;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

public class RabbitMQConfig {

    private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    public static Sender createSender() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        SenderOptions senderOptions = new SenderOptions()
                .connectionFactory(factory);

        return RabbitFlux.createSender(senderOptions);
    }

    public static Receiver createReceiver() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        ReceiverOptions receiverOptions = new ReceiverOptions()
                .connectionFactory(factory);

        return RabbitFlux.createReceiver(receiverOptions);
    }
}
