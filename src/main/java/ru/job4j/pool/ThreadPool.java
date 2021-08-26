package ru.job4j.pool;

import ru.job4j.concurrent.prodconc.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int capacity = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(capacity);

    public ThreadPool() {
        for (int i = 0; i < capacity; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println("task1 started"));
        threadPool.work(() -> System.out.println("task2 started"));
        threadPool.work(() -> System.out.println("task3 started"));
        threadPool.work(() -> System.out.println("task3 started"));
    }
}
