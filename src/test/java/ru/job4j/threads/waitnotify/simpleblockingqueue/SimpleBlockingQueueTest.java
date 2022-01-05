package ru.job4j.threads.waitnotify.simpleblockingqueue;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    /**
     * В данном тестовом методе с помощью start() запускаются на исполнение
     * нити producerThread и consumerThread. После этого,
     * главный поток дожидается, когда метод run() нити
     * producerThread отработает полностью(пройдет все итерации цикла
     * по наполнению очереди значениями). Затем, перейдя в следующую строку,
     * главный поток исполнения дожидается полного завершения работы
     * метода run() нити consumerThread(из очереди достаются все
     * элементы, которые были туда помещены нитью producerThread).
     * Иначе, если не использовать метод join() на нитях producerThread
     * и consumerThread, то за время, пока главный поток достигнет конца
     * метода simpleBlockQueueTest(), методы run() этих нитей успеют отработать
     * лишь малую долю своей логики.
     * <p>
     * При запуске аналогичного кода в обычном методе main
     * использовать join() не нужно, так как программа завершает
     * всю свою деятельность и выходит, когда происходит одна из двух вещей:
     * 1) Все потоки, которые не являются потоками демона, завершаются.
     * 2) В некотором потоке вызывается метод exit класса Runtime или класс System,
     * а операция выхода не запрещена менеджером безопасности.
     *
     * @throws InterruptedException
     */
    @Test
    public void simpleBlockQueueTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Producer producer = new Producer(simpleBlockingQueue);
        Consumer consumer = new Consumer(simpleBlockingQueue);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        Thread.sleep(100);
        consumerThread.start();
        producerThread.join();
        consumerThread.join();
        assertThat(simpleBlockingQueue.isEmpty(), is(true));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenFetchAllThenGetLastValue() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 7).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                                Thread.currentThread().sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        );
        Thread consumer = new Thread(
                () -> {
                    while (queue.getSizeQueue() > 1 || !Thread.currentThread().isInterrupted()) {
                        try {
                            queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    try {
                        buffer.add(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        producer.join();
        consumer.start();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(List.of(6)));
    }
}