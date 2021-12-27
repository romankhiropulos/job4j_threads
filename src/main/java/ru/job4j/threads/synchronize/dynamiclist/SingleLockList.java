package ru.job4j.threads.synchronize.dynamiclist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.*;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>, Serializable {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        Objects.requireNonNull(list);
        this.list = new ArrayList<>(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy().iterator();
    }

    private synchronized List<T> copy() {
        return new ArrayList<>(list);
    }
}
