package ru.job4j.threads.pool.threadpool;

import ru.job4j.threads.waitnotify.simpleblockingqueue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final int size = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        prepareThreadPool();
    }

    private void prepareThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " started!");
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is running...");
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " interrupted!");
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Thread_".concat(String.valueOf(i))));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        try {
            Thread.sleep(3000);
            pool.work(() -> System.out.println("Task 1"));
            Thread.sleep(3000);
            pool.work(() -> System.out.println("Task 2"));
            pool.work(() -> System.out.println("Task 3"));
            pool.work(() -> System.out.println("Task 4"));
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
