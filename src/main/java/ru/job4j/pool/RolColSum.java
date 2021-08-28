package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
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
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSums(matrix, i, i);
        }
        return sums;
    }

    private static Sums getSums(int[][] matrix, int rowStart, int columnStart) {
        int rowSum = 0;
        int colSum = 0;
        for (int column = 0; column < matrix.length; column++) {
            rowSum += matrix[rowStart][column];
            if (column == columnStart) {
                for (int row = 0; row < matrix.length; row++) {
                    colSum += matrix[row][column];
                }
            }
        }
        return new Sums(rowSum, colSum);
    }

    private static Sums completableFuture(int[][] matrix, int rowStart, int columnStart) throws ExecutionException, InterruptedException {
        CompletableFuture<Sums> sum = CompletableFuture.supplyAsync(() ->
                getSums(matrix, rowStart, columnStart)
        );
        return sum.get();
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = completableFuture(matrix, i, i);
        }
        return sums;
    }
}

