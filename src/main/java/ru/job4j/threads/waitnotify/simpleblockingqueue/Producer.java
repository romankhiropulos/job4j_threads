package ru.job4j.threads.waitnotify.simpleblockingqueue;

public class Producer implements Runnable {

    private final SimpleBlockingQueue simpleBlockingQueue;

    public Producer(SimpleBlockingQueue simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            simpleBlockingQueue.offer(i);
            System.out.println("Create offer: " + i);
        }
    }
}