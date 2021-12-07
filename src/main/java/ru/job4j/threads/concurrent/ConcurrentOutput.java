package ru.job4j.threads.concurrent;

public class ConcurrentOutput {

    public static void main(String[] args) {
        Thread another = new Thread(() -> printCurrentThreadName(10));
        Thread second = new Thread(() -> printCurrentThreadName(10));
        another.start();
        second.start();
        printCurrentThreadName(10);
    }

    private static void printCurrentThreadName(int countOfHowManyTimes) {
        if (countOfHowManyTimes < 1) {
            System.out.println(Thread.currentThread().getName());
        } else {
            for (int i = 0; i < countOfHowManyTimes; i++) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}