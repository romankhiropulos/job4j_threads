package ru.job4j.threads.synchronize.usercache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> userStorage = new HashMap<>();

    public synchronized boolean add(User user) {
        return this.userStorage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return this.userStorage.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return this.userStorage.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFrom = Objects.requireNonNull(this.userStorage.get(fromId), String.valueOf(fromId));
        User userTo = Objects.requireNonNull(this.userStorage.get(toId), String.valueOf(toId));
        if (userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
        } else {
            throw new IllegalArgumentException("Not enough money in the user account: " + fromId);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserStorage storage = new UserStorage();

        Thread threadAdd = new Thread(() -> {
            storage.add(new User(1, 100));
            storage.add(new User(2, 200));
        });
        Thread threadTransfer = new Thread(() -> {
            storage.transfer(1, 2, 50);
        });

        threadAdd.start();
        threadAdd.join();
        threadTransfer.start();
        threadTransfer.join();
    }
}
