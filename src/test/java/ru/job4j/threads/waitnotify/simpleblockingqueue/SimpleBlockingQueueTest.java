package ru.job4j.threads.waitnotify.simpleblockingqueue;

import org.junit.Test;

public class SimpleBlockingQueueTest {

    @Test
    public void simpleBlockQueueTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Producer producer = new Producer(simpleBlockingQueue);
        Consumer consumer = new Consumer(simpleBlockingQueue);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();
    }
}