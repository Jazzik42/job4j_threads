package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class WgetNew implements Runnable {
    private final String url;
    private final int speed;

    public WgetNew(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream io = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("newFile.xml")) {
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

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetNew(url, speed));
        wget.start();
        wget.join();
    }
}
