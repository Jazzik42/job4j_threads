package ru.job4j.pool;

import ru.job4j.concurrent.prodconc.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private int capacity;
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(capacity);

    public ThreadPool(int capacity) {
        this.capacity = capacity;
        for (int i = 0; i < getPoolSize(); i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("thread started");
                    try {
                        tasks.poll().run();
                        System.out.println("thread выполнил свою работу");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getPoolSize() {
        return Runtime.getRuntime().availableProcessors();
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }


    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(4);
        threadPool.work(() -> System.out.println("task1 started"));
        threadPool.work(() -> System.out.println("task2 started"));
        threadPool.work(() -> System.out.println("task3 started"));
        threadPool.work(() -> System.out.println("task3 started"));
        threadPool.shutdown();
    }
}
