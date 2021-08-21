package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        System.out.println(count);
        notifyAll();
    }

    public synchronized void await() {
        while (count < total) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " is runnable.");
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(10);
        Thread thread1 = new Thread(() -> {
            while (countBarrier.count < countBarrier.total) {
                countBarrier.count();
            }
        }
        );
        Thread thread2 = new Thread(
                countBarrier::await
        );
        thread1.start();
        thread2.start();
    }
}
