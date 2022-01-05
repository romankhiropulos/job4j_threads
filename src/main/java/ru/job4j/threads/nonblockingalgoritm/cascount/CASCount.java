package ru.job4j.threads.nonblockingalgoritm.cascount;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer current;
        Integer newCount;
        do {
            current = count.get();
            newCount = ++current;
            if (current == 0) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
        } while (!count.compareAndSet(current, newCount));
    }

    public int get() {
        int current;
        do {
            current = count.get();
            if (current == 0) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
        } while (!count.compareAndSet(current, current));
        return current;
    }
}
