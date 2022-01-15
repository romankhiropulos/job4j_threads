package ru.job4j.threads.completablefuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sum = new Sums();
            for (int j = 0; j < matrix[i].length; j++) {
                sum.rowSum += matrix[i][j];
                sum.colSum += matrix[j][i];
            }
            sums[i] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n; i++) {
            futures.put(i, getTask(matrix, i, i));
        }
        for (Map.Entry<Integer, CompletableFuture<Sums>> cf : futures.entrySet()) {
            sums[cf.getKey()] = cf.getValue().get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int rowNumber, int colNumber) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                sum.rowSum += matrix[rowNumber][j];
                sum.colSum += matrix[j][colNumber];
            }
            return sum;
        });
    }

    /**
     * - sums[i].rowSum - сумма элементов по i строке
     * - sums[i].colSum  - сумма элементов по i столбцу
     */
    public static class Sums {

        private int rowSum;

        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Sums.class.getSimpleName() + "[", "]")
                    .add("rowSum=" + rowSum)
                    .add("colSum=" + colSum)
                    .toString();
        }
    }
}
