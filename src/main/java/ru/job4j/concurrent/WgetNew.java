package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class WgetNew implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public WgetNew(String[] args) {
        String[] arguments = validateArgs(args);
        this.url = arguments[0];
        this.speed = Integer.parseInt(arguments[1]);
        this.fileName = arguments[2];
    }

    @Override
    public void run() {
        try (BufferedInputStream io = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int byteRead;
            long startTime = System.nanoTime();
            while ((byteRead = io.read(dataBuffer, 0, 1024)) != -1) {
                long endTime = System.nanoTime();
                out.write(dataBuffer, 0, byteRead);
                long duration = endTime - startTime;
                if (duration < speed) {
                    try {
                        Thread.sleep(speed - duration);
                    } catch (InterruptedException a) {
                        a.printStackTrace();
                    }
                }
                startTime = System.nanoTime();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] validateArgs(String[] args) {
        if (!args[0].startsWith("https://")
                || !args[1].matches("\\d+")
                || !args[2].endsWith(".xml")) {
            throw new IllegalArgumentException();
        }
        return args;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread wget = new Thread(new WgetNew(args));
        wget.start();
        wget.join();
    }
}
