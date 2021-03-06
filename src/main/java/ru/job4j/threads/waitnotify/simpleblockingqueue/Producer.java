package ru.job4j.threads.waitnotify.simpleblockingqueue;

public class Producer implements Runnable {

    private final SimpleBlockingQueue simpleBlockingQueue;

    public Producer(SimpleBlockingQueue simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(100);
                simpleBlockingQueue.offer(i);
                System.out.println("Create offer: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}