package ru.job4j.store;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;

public class UserStore implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    @GuardedBy("this")
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(),
                User.of(user.getId(), user.getAmount())) == null;
    }

    @Override
    @GuardedBy("this")
    public synchronized boolean update(User user) {
        return users.computeIfPresent(user.getId(),
                (key, value) ->
                        value = User.of(user.getId(), user.getAmount())) != null;
    }

    @Override
    @GuardedBy("this")
    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @Override
    @GuardedBy("this")
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            return update(User.of(fromId, fromUser.getAmount() - amount))
                    && update(User.of(toId, toUser.getAmount() + amount));
        }
        return false;
    }

    public static void main(String[] args) {
        UserStore us = new UserStore();
        us.add(User.of(1, 100));
        us.add(User.of(2, 200));
        System.out.println(us.transfer(1, 2, 230));

    }
}
