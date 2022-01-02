package ru.job4j.threads.waitnotify.simpleblockingqueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Producer producer = new Producer(simpleBlockingQueue);
        Consumer consumer = new Consumer(simpleBlockingQueue);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
    }
}
