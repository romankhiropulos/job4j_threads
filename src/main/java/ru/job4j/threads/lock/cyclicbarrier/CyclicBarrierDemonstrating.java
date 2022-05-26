package ru.job4j.threads.lock.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemonstrating {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cb =
                new CyclicBarrier(3, new BarAction());
        System.out.println("Зaпycк потоков");
        new MyThread(cb, "A").start();
        new MyThread(cb, "B").start();
        new MyThread(cb, "С").start();
        Thread.sleep(10);
        new MyThread(cb, "Х").start();
        new MyThread(cb, "У").start();
        new MyThread(cb, "Z").start();
    }
}

class BarAction implements Runnable {
    public void run() {
        System.out.println("Бapьep достигнут!");
    }
}

class MyThread extends Thread {

    CyclicBarrier cbar;

    String name;

    MyThread(CyclicBarrier c, String n) {
        cbar = c;
        name = n;
    }

    public void run() {
        System.out.println(name.concat(" before await"));
        try {
            cbar.await();
            System.out.println(name.concat(" after await"));
        } catch (BrokenBarrierException | InterruptedException ехс) {
            System.out.println(ехс);
        }
    }
}