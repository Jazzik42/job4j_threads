package ru.job4j.store;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UserStoreTest {

    @Test
    public void whenAddAndGet() throws InterruptedException {
        UserStore userStore = new UserStore();
        Thread thread1 = new Thread(
                () -> userStore.add(new User(1, 100))
        );
        Thread thread2 = new Thread(
                () -> userStore.add(new User(2, 200))
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStore.get(2).getAmount(), is(200));
    }

    @Test
    public void whenUpdate() throws InterruptedException {
        UserStore userStore = new UserStore();
        Thread thread1 = new Thread(
                () -> {
                    synchronized (userStore) {
                        userStore.add(new User(1, 100));
                        userStore.update(new User(1, 150));
                        userStore.notify();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    synchronized (userStore) {
                        while (userStore.get(1) == null) {
                            try {
                                userStore.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        userStore.update(new User(1, 250));
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStore.get(1).getAmount(), is(250));
    }


    @Test
    public void whenDelete() throws InterruptedException {
        UserStore userStore = new UserStore();
        Thread thread1 = new Thread(
                () -> {
                    synchronized (userStore) {
                        userStore.add(new User(1, 100));
                        userStore.add(new User(2, 100));
                        userStore.delete(new User(1, 100));
                        userStore.notify();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    synchronized (userStore) {
                        while (userStore.get(2) == null) {
                            try {
                                userStore.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        userStore.delete(new User(2, 100));
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertNull(userStore.get(1));
    }

    @Test
    public void whenTransfer() throws InterruptedException {
        UserStore userStore = new UserStore();
        userStore.add(new User(1, 100));
        userStore.add(new User(2, 200));
        Thread thread1 = new Thread(
                () -> userStore.transfer(1, 2, 100)
        );
        Thread thread2 = new Thread(
                () -> userStore.transfer(2, 1, 100)
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStore.get(2).getAmount(), is(200));
    }
}
