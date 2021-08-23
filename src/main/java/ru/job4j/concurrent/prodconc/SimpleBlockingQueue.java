package ru.job4j.concurrent.prodconc;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int initialCapacity;

    public SimpleBlockingQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= initialCapacity) {
            wait();
        }
        System.out.println("offer 1");
        notifyAll();
        queue.offer(value);
        System.out.println("offer 2");
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("pool 1");
            wait();
            System.out.println("pool 2");
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

