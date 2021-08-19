package ru.job4j.synch;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(arrayList);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void get() throws InterruptedException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(arrayList);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(list.get(1), is(2));
    }

    @Test
    public void iterator() throws InterruptedException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(arrayList);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        Iterator<Integer> it = list.iterator();
        Thread third = new Thread(() -> list.add(3));
        third.start();
        third.join();
        it.forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }
}
