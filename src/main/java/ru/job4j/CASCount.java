package ru.job4j;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount() {
        count.set(0);
    }

    public void increment() {
        int oldValue;
        do {
            oldValue = count.get();
        } while (!count.compareAndSet(oldValue, oldValue + 1));
    }

    public int get() {
        return count.get();
    }
}
