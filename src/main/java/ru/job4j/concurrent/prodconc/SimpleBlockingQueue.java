package ru.job4j.concurrent.prodconc;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private volatile int count = 0;

    public synchronized void offer(T value) {
        while (count >= 10) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.offer(value);
        count++;
        this.notify();
    }

    public synchronized T poll() {
        while (count < 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        T rsl = queue.poll();
        this.notify();
        return rsl;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

