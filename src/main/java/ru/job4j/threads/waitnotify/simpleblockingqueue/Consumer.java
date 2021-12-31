package ru.job4j.threads.waitnotify.simpleblockingqueue;

public class Consumer implements Runnable {

    private final SimpleBlockingQueue simpleBlockingQueue;

    public Consumer(SimpleBlockingQueue simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(500);
                var value = simpleBlockingQueue.poll();
                System.out.println("Get item: " + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}