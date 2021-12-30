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

    public synchronized void offer(T value) {
        while (this.queue.size() == this.sizeLimit) {
            try {
                wait();
                System.out.println("Producer waiting...");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        if (this.queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(value);
    }

    public synchronized T poll() {
        while (this.queue.size() == 0) {
            try {
                wait();
                System.out.println("Consumer waiting...");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        if (this.queue.size() == this.sizeLimit) {
            notifyAll();
        }
        return this.queue.poll();
    }
}