package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddAndUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        assertTrue(cache.update(new Base(2, 1)));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateThrowOptimisticEx()  {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        cache.update(new Base(2, 2));
    }

    @Test
    public void whenDeleteAndUpdateReturnFalse()  {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        cache.delete(base2);
        assertFalse(cache.update(new Base(2, 1)));
    }
}
