package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = String.format("Notification {%s} to email {%S}",
                user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to {%S}",
                user.getUsername());
        pool.submit(() ->
                send(subject, body, user.getUsername())
        );
    }

    public void send(String subject, String body, String name) {
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

