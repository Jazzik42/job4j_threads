package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public SearchIndex(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    private int indexOF() {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if ((to - from) < 10) {
            return indexOF();
        }
        int mid = (to + from) / 2;

        SearchIndex<T> fjp1 = new SearchIndex<>(array, from, mid, value);
        SearchIndex<T> fjp2 = new SearchIndex<>(array, mid + 1, to, value);
        fjp1.fork();
        fjp2.fork();
        return Math.max(fjp1.join(), fjp2.join());
    }

    public static <T> Integer search(T[] array, T value) {
        ForkJoinPool fJp = new ForkJoinPool();
        return fJp.invoke(new SearchIndex<>(array, 0, array.length - 1, value));
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 5, 4, 3, 5, 6, 8, 10, 11, 2, 5, 3, 4};
        System.out.println(SearchIndex.search(array, 11));
        String[] strings = new String[]{"a", "b", "c", "d", "e", "f", "h", "g", "12", "4", "5", "b", "c"};
        System.out.println(SearchIndex.search(strings, "g"));
    }
}
