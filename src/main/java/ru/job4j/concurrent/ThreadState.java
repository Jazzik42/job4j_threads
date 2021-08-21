package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState() + " first");
            System.out.println(second.getState() + " second");
        }
        System.out.println(first.getState() + " first");
        System.out.println(second.getState() + " second");
        System.out.println(Thread.currentThread().getName() + " работа завершена");
    }
}
