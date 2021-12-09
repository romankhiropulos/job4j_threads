package ru.job4j.threads.commonresources;

public final class Cache {

    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(() -> System.out.println(instOf() + " from threadFirst"));
        Thread second = new Thread(() -> System.out.println(instOf() + " from threadSecond"));
        first.start();
        second.start();
    }
}