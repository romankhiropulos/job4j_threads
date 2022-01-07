package ru.job4j.threads.nonblockingalgoritm.cascount;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int current;
        int newCount;
        do {
            current = count.get();
            newCount = current + 1;
        } while (!count.compareAndSet(current, newCount));
    }

    public int get() {
        return count.get();
    }
}
