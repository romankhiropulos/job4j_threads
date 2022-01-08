package ru.job4j.threads.nonblockingalgoritm.cascache;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddThenNameIsName1() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("Name1");
        cache.add(model);
        assertThat(cache.getValues().get(0).getName(), is("Name1"));
    }

    @Test
    public void whenUpdate200TimesThenVersionIs200() throws InterruptedException {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        model.setName("Name1");
        cache.add(model);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                cache.update(model);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                cache.update(model);
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(4000);
        assertThat(cache.getValues().get(0).getVersion(), is(200));
    }

    @Test
    public void whenDeleteOneThenLastWithNameName2() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(2, 1);
        model1.setName("Name1");
        model2.setName("Name2");
        cache.add(model1);
        cache.add(model2);
        cache.delete(model1);
        assertThat(cache.getValues().get(0).getName(), is("Name2"));
    }

    @Test(expected = OptimisticException.class)
    public void whenWhenVersionNotSimilarThenGetOptimisticException() throws Exception {
        AtomicReference<Exception> exception = new AtomicReference<>();
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        model.setName("Name1");
        cache.add(model);
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    cache.update(model);
                }
            } catch (OptimisticException optimisticException) {
                exception.set(optimisticException);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    cache.update(new Base(model.getId(), 0));
                }
            } catch (OptimisticException optimisticException) {
                exception.set(optimisticException);
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(4000);
        throw exception.get();
    }
}