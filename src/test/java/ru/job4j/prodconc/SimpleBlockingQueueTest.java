package ru.job4j.prodconc;

import org.junit.Test;
import ru.job4j.concurrent.prodconc.SimpleBlockingQueue;

import static org.junit.Assert.assertNotNull;

public class SimpleBlockingQueueTest {

    @Test
    public void whenPollAndOfferMultithreading() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer1 = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread producer2 = new Thread(
                () -> {
                    try {
                        queue.offer(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer1.start();
        producer2.start();
        consumer.start();
        producer1.join();
        producer2.join();
        consumer.join();
        assertNotNull(queue.poll());
    }

    @Test
    public void whenAddFullCapacity() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer1 = new Thread(
                () -> {
                    try {
                        for (int i = 1; i <= 11; i++) {
                            queue.offer(i);
                            System.out.println(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        for (int i = 1; i <= 11; i++) {
                            System.out.println("Return" + queue.poll());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer1.start();
        consumer.start();
        producer1.join();
        consumer.join();
    }
}
