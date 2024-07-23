package org.example.consumer;

import java.time.Duration;

import org.example.config.RabbitMQConfig;
import org.example.model.PurchaseOrder;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Receiver;

public class PurchaseOrderConsumer {

    private static final String PO_QUEUE_NAME = "purchase_orders";
    private static final String VEHICLE_QUEUE_NAME = "vehicle_queue";

    public static void consume() {
        Receiver receiver = RabbitMQConfig.createReceiver();

        receiver.consumeAutoAck(PO_QUEUE_NAME)
                .map(d -> new String(d.getBody()))
                .map(PurchaseOrder::fromString)
                .doOnNext(po -> System.out.println("Received PO: " + po))
                .doOnDiscard(PurchaseOrder.class, po -> System.out.println("Discarded invalid PO: " + po))
                .map(po -> new OutboundMessage("", VEHICLE_QUEUE_NAME, po.toString().getBytes()))
                .timeout(Duration.ofSeconds(30))
                .doOnError(e -> System.err.println("Error occurred: " + e.getMessage()))
                .doFinally(signalType -> {
                    System.out.println("Consumer in finally for signal: " + signalType);
                    receiver.close();
                })
                .subscribe();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
