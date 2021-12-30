package ru.job4j.threads.waitnotify.simpleblockingqueue;

public class Consumer implements Runnable {

    private final SimpleBlockingQueue simpleBlockingQueue;

    public Consumer(SimpleBlockingQueue simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            var value = simpleBlockingQueue.poll();
            System.out.println(value);
        }
    }
}