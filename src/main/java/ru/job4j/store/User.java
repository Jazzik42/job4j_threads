package ru.job4j.store;

import java.util.Objects;

public class User {
    private final int id;
    private final int amount;

    private User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public static User of(int id, int amount) {
        return new User(id, amount);
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", balance=" + amount
                + '}';
    }
}
