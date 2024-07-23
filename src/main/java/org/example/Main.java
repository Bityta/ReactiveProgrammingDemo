package org.example;

import org.example.consumer.PurchaseOrderConsumer;
import org.example.produced.PurchaseOrderGenerator;

public class Main {
    public static void main(String[] args) {
        Thread generatorThread = new Thread(PurchaseOrderGenerator::start);
        Thread consumerThread = new Thread(PurchaseOrderConsumer::consume);

        generatorThread.start();
        consumerThread.start();

        try {
            generatorThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
