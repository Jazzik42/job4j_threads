package ru.job4j.pool;

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
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int column = 0; column < matrix.length; column++) {
                rowSum += matrix[i][column];
                if (column == i) {
                    for (int row = 0; row < matrix.length; row++) {
                        colSum += matrix[row][column];
                    }
                }
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    private static Sums getSumAsync(int[][] matrix, int rowStart, int columnStart) throws ExecutionException, InterruptedException {
        CompletableFuture<Sums> sum = CompletableFuture.supplyAsync(() -> {
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
        });
        return sum.get();
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSumAsync(matrix, i, i);
        }
        return sums;
    }
}

