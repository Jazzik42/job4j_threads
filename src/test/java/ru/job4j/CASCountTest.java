package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    @Test
    public void when() {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(
                casCount::increment
        );
        Thread thread2 = new Thread(
                casCount::increment
        );
        Thread thread3 = new Thread(
                casCount::increment
        );
        Thread thread4 = new Thread(
                casCount::increment
        );
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(casCount.get(), is(4));
    }
}
