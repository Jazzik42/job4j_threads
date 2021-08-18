package ru.job4j.store;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public interface UserStorage {

    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    boolean transfer(int fromId, int toId, int amount);
}
