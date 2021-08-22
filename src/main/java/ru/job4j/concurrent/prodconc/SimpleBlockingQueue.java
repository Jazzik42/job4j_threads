package ru.job4j.concurrent.prodconc;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int initialCapacity;

    public SimpleBlockingQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= initialCapacity) {
            this.wait();
        }
        queue.offer(value);
        this.notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        T rsl = queue.poll();
        this.notify();
        return rsl;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

