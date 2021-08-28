package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RolColSumTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        RolColSum.Sums[] sumsExp = new RolColSum.Sums[]{
                new RolColSum.Sums(6, 3),
                new RolColSum.Sums(6, 6),
                new RolColSum.Sums(6, 9)
        };
        assertThat(RolColSum.asyncSum(matrix), is(sumsExp));
    }
}

