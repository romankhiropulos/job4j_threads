package ru.job4j.threads.forkjoinpool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelBinarySearch<T extends Comparable<T>> extends RecursiveTask<Integer> {

    private final T[] array;

    private final T target;

    private final int first;

    private final int last;

    public ParallelBinarySearch(T[] array, T target, int first, int last) {
        Objects.requireNonNull(array);
        Objects.requireNonNull(target);
        this.array = array;
        this.target = target;
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        if (first > last) {
            return -1;
        }
//        else if (array.length < 11) {
//            for (int i = 0; i < array.length; i++) {
//                if (target.compareTo(array[i]) == 0) {
//                    return i;
//                }
//            }
//            return -1;
//        }
        else {
            int middle = (first + last) / 2;
            if (target.compareTo(array[middle]) == 0) {
                return middle;
            }
            ParallelBinarySearch<T> leftSearch = new ParallelBinarySearch<T>(array, target, first, middle);
            ParallelBinarySearch<T> rightSearch = new ParallelBinarySearch<T>(array, target, middle + 1, last);
            leftSearch.fork();
            rightSearch.fork();
            int left = leftSearch.join();
            int right = rightSearch.join();
            return left != -1 ? left : right;
        }
    }

    public static void main(String[] args) {
        String[] names = {"Caryn", "Debbie", "Dustin", "Elliot", "Jacquie", "Jonathan", "Rich"};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer res = forkJoinPool.invoke(
                new ParallelBinarySearch<>(names, "Jacquie", 0, names.length - 1)
        );
        System.out.println(res);
    }
}
