package ru.job4j.threads.concurrent;

public class ThreadSleep {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        Thread.sleep(500);
        System.out.println(thread.getState());
        System.out.println("Main");
    }
}
