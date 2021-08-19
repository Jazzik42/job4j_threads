package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

@ThreadSafe
public class UserStore implements UserStorage {

    @GuardedBy("this")
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public synchronized boolean update(User user) {
        return users.computeIfPresent(user.getId(),
                (key, value) -> value = user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            return update(new User(fromId, fromUser.getAmount() - amount))
                    && update(new User(toId, toUser.getAmount() + amount));
        }
        return false;
    }

    public synchronized User get(int id) {
        return users.get(id);
    }
}
