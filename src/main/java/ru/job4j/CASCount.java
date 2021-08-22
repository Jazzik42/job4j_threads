package ru.job4j;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int oldValue;
        int newValue;
        do {
            oldValue = count.get();
            newValue = oldValue++;
        } while (!count.compareAndSet(oldValue, newValue));
    }

    public int get() {
        return count.get();
    }
}
