package ru.job4j.threads.waitnotify.simpleblockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    @GuardedBy("this")
    private int sizeLimit = 10;

    public SimpleBlockingQueue() {
    }

    public SimpleBlockingQueue(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public synchronized int getSizeQueue() {
        return this.queue.size();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() >= this.sizeLimit) {
            wait();
            System.out.println("Producer waiting...");
        }
        this.queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
            System.out.println("Consumer waiting...");
        }
        var item = this.queue.poll();
        notifyAll();
        return item;
    }
}