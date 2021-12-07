package ru.job4j.threads.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(() -> printCurrentThreadName(50), "First thread");
        Thread second = new Thread(() -> printCurrentThreadName(50), "Second thread");
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        System.out.println(first.getState());
        System.out.println(second.getState());
        printCurrentThreadName(20);
        while (true) {
            if (first.getState() == Thread.State.TERMINATED && second.getState() == Thread.State.TERMINATED) {
                System.out.println(
                        "First thread state: " + first.getState() + "; Second thread state: " + second.getState()
                );
                System.out.println("Работа завершена!");
                break;
            }
        }
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