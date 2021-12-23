package ru.job4j.threads.synchronize.usercache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> userStorage = new HashMap<>();

    public synchronized boolean add(User user) {
      this.userStorage.put(user.getId(), user);
      return this.userStorage.get(user.getId()) != null;
    }

    public synchronized boolean update(User user) {
        return this.userStorage.put(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return this.userStorage.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        Optional.of(this.userStorage.get(fromId)).ifPresent(u -> u.setAmount(u.getAmount() - amount));
        Optional.of(this.userStorage.get(toId)).ifPresent(u -> u.setAmount(u.getAmount() + amount));
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
