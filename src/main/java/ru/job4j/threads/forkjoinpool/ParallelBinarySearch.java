package ru.job4j.threads.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelBinarySearch<T extends Comparable<T>> extends RecursiveTask<Integer> {

    private final T[] array;

    private final T key;

    private final int startIndex;

    private final int endIndex;

    private static final int THRESHOLD = 10;

    private ParallelBinarySearch(T[] array, T key, int startIndex, int endIndex) {
        this.array = array;
        this.key = key;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Integer compute() {

        if ((endIndex - startIndex) < THRESHOLD) {
            return linearSearch();
        }
        int middle = startIndex + (endIndex - startIndex) / 2;
        ParallelBinarySearch<T> left = new ParallelBinarySearch<>(array, key, startIndex, middle);
        ParallelBinarySearch<T> right = new ParallelBinarySearch<>(array, key, middle + 1, endIndex);
        left.fork();
        right.fork();
        int leftIndex = left.join();
        int rightIndex = right.join();
        return leftIndex != -1 ? leftIndex : rightIndex;
    }

    private int linearSearch() {
        for (int i = startIndex; i <= endIndex; i++) {
            if (array[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public static <T extends Comparable<? super T>> int sort(T[] array, T key) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (int) forkJoinPool.invoke(
                new ParallelBinarySearch(array, key, 0, array.length - 1)
        );
    }
}
