package ru.job4j.threads.synchronize.dynamiclist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        Objects.requireNonNull(list);
        this.list = copy(list);
    }

    public void add(T value) {

    }

    public T get(int index) {
        return null;
    }

    @Override
    public synchronized  Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    private List<T> copy(List<T> list) {
        return list;
    }
}
