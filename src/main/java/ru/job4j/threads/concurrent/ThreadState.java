package ru.job4j.threads.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(() -> printCurrentThreadName(1000), "First thread");
        Thread second = new Thread(() -> printCurrentThreadName(1000), "Second thread");
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        while (!(first.getState() == Thread.State.TERMINATED && second.getState() == Thread.State.TERMINATED)) {
            System.out.println(
                    "First thread state: " + first.getState() + "; Second thread state: " + second.getState()
            );
        }
        System.out.println("Работа завершена!");
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