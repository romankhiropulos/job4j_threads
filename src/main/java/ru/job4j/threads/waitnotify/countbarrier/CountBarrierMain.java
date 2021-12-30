package ru.job4j.threads.waitnotify.countbarrier;

public class CountBarrierMain {

    public static void main(String[] args) {

        CountBarrier barrier = new CountBarrier(100);
        Thread counter = new Thread(barrier::count);
        Thread await1 = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName());
                },
                "await1"
        );
        Thread await2 = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName());
                },
                "await2"
        );
        await1.start();
        await2.start();
        counter.start();
    }
}
