package ru.job4j.threads.synchronize.dynamiclist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.*;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>, Serializable {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) throws IOException, ClassNotFoundException {
        Objects.requireNonNull(list);
        this.list = copyList(list);
    }

    public synchronized void add(T value) throws IOException, ClassNotFoundException {
        this.list.add((T) convertFromBytes(convertToBytes(value)));
    }

    public synchronized SingleLockList<T> copy() throws IOException, ClassNotFoundException {
        return this.getClone();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return this.list.iterator();
    }

    public synchronized T get(int index) throws IOException, ClassNotFoundException {
        return (T) convertFromBytes(convertToBytes(this.list.get(index)));
    }

    private List<T> copyList(List<T> list) throws IOException, ClassNotFoundException {
        return (List<T>) convertFromBytes(convertToBytes(list));
    }

    private SingleLockList<T> getClone() throws IOException, ClassNotFoundException {
        return (SingleLockList<T>) convertFromBytes(convertToBytes(this));
    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }
}
