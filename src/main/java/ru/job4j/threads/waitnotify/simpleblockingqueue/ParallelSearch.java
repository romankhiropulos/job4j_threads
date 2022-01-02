package ru.job4j.threads.waitnotify.simpleblockingqueue;

/**
 * На строке 19 ловим InterruptedException в тот момент, когда
 * очередь ждет в методе poll(), а строка 37 уже отработала (InterruptedException – if any thread interrupted
 * the current thread before or while the current thread was waiting.
 * The interrupted status of the current thread is cleared when this exception is thrown)
 */
public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 9; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
