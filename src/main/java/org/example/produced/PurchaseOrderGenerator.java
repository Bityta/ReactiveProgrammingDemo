package org.example.produced;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import org.example.config.RabbitMQConfig;
import org.example.model.PurchaseOrder;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

public class PurchaseOrderGenerator {

    private static final String QUEUE_NAME = "purchase_orders";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final Random RANDOM = new Random();

    private static PurchaseOrder generateRandomPurchaseOrder() {
        return new PurchaseOrder(
                UUID.randomUUID().toString(),
                RANDOM.nextInt() * 100000,
                RANDOM.nextBoolean() ? "Car" : (RANDOM.nextBoolean() ? "Truck" : "Motorcycle"),
                LocalDateTime.now()
        );
    }

    private static String purchaseOrderToString(PurchaseOrder po) {
        return String.format("%s,%d,%s,%s",
                po.getId(),
                po.getAmount(),
                po.getType(),
                po.getTimestamp().format(DATE_TIME_FORMATTER));
    }

    public static Flux<String> generatePurchaseOrders() {
        return Flux.interval(Duration.ofMillis(100))
                .take(Duration.ofSeconds(10).toMillis() / 100)
                .map(tick -> {
                    PurchaseOrder po = generateRandomPurchaseOrder();
                    return purchaseOrderToString(po);
                });
    }

    public static void start() {
        Sender sender = RabbitMQConfig.createSender();

        sender.sendWithPublishConfirms(
                        generatePurchaseOrders()
                                .doOnNext(orderString -> System.out.println("Produced: " + orderString))  // Печать строки заказа
                                .map(orderString -> new OutboundMessage("", QUEUE_NAME, orderString.getBytes()))
                                .doFinally(signal -> {
                                    System.out.println("Generator finished with signal " + signal);
                                    sender.close();
                                })
                )
                .doOnError(e -> System.err.println("Error: " + e.getMessage()))
                .subscribe();

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
