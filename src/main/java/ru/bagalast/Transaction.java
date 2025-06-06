package ru.bagalast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime time;
    private final String user;
    private final String operation;
    private final double amount;
    private final String otherUser;

    public Transaction(LocalDateTime time, String user, String operation, double amount, String otherUser) {
        this.time = time;
        this.user = user;
        this.operation = operation;
        this.amount = amount;
        this.otherUser = otherUser;
    }

    @Override
    public String toString() {
        if ("transferred".equals(operation)) {
            return String.format("[%s] %s %s %.2f to %s",
                    time.format(DATE_FORMATTER),
                    user,
                    operation,
                    amount,
                    otherUser
            );
        } else if ("received".equals(operation)) {  // Добавь этот случай, уёбок
            return String.format("[%s] %s received %.2f from %s",
                    time.format(DATE_FORMATTER),
                    user,
                    amount,
                    otherUser
            );
        } else {
            return String.format("[%s] %s %s %.2f",
                    time.format(DATE_FORMATTER),
                    user,
                    operation,
                    amount
            );
        }
    }

    public double getAmount() {
        return amount;
    }
    public String getOperation(){
        return operation;
    }
    public LocalDateTime getTime(){
        return time;
    }
    public String getOtherUser() {
        return otherUser;
    }
    public String getUser() {
        return user;
    }
}
