package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseOrder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String id;
    private int amount; // Изменено на int
    private String type;
    private LocalDateTime timestamp;

    public PurchaseOrder(String id, int amount, String type, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public static PurchaseOrder fromString(String str) {
        String[] parts = str.split(",");
        if (parts.length != 4) {
            System.err.println("Invalid format: " + str);
            throw new IllegalArgumentException("Invalid PurchaseOrder string format");
        }

        String id = parts[0].trim();
        int amount = Integer.parseInt(parts[1].trim()); // Изменено на int
        String type = parts[2].trim();
        LocalDateTime timestamp = LocalDateTime.parse(parts[3].trim(), formatter);

        return new PurchaseOrder(id, amount, type, timestamp);
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%s,%s", id, amount, type, timestamp.format(formatter));
    }
}
