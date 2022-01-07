package ru.job4j.threads.nonblockingalgoritm.cascache;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddThenNameIsName1() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Name1");
        cache.add(base);
        assertThat(cache.getValues().get(0).getName(), is("Name1"));
    }

    @Test
    public void whenUpdate200TimesThenVersionIs200() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("Name1");
        cache.add(base);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                base.setName("Name");
                cache.update(base);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                base.setName("Name");
                cache.update(base);
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
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        base1.setName("Name1");
        base2.setName("Name2");
        cache.add(base1);
        cache.add(base2);
        cache.delete(base1);
        assertThat(cache.getValues().get(0).getName(), is("Name2"));
    }
}