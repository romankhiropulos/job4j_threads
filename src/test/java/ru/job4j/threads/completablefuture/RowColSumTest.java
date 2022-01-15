package ru.job4j.threads.completablefuture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RowColSumTest {

    private int[][] matrix;

    private RowColSum.Sums[] sums;

    @Before
    public void prepareMatrix() {
        matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        sums = new RowColSum.Sums[]{
                new RowColSum.Sums(6, 12),
                new RowColSum.Sums(15, 15),
                new RowColSum.Sums(24, 18)
        };
    }

    @After
    public void clearMatrix() {
        matrix = null;
        sums = null;
    }

    @Test
    public void whenAskLinearSum() {
        assertThat(sums, is(RowColSum.sum(matrix)));
    }

    @Test
    public void whenAskAsyncSum() {
        try {
            assertThat(sums, is(RowColSum.asyncSum(matrix)));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}