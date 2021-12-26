package ru.job4j.threads.synchronize.dynamiclist;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException, IOException, ClassNotFoundException {
        List<Integer> listNotThreadSafe = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(listNotThreadSafe);
        Thread first = new Thread(() -> {
            try {
                list.add(1);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Thread second = new Thread(() -> {
            try {
                list.add(2);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }
}