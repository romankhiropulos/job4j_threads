package ru.job4j.threads.nonblockingalgoritm.cascount;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrement200TimesThenCountIs200() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                casCount.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                casCount.increment();
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(4000);
        assertThat(casCount.get(), is(200));
    }

    @Test
    public void whenCountIsInitialThenCountIs0() {
        CASCount casCount = new CASCount();
        assertThat(casCount.get(), is(0));
    }
}