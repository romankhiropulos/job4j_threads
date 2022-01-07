package ru.job4j.threads.nonblockingalgoritm.cascache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (id, base) -> {
            if (base.getVersion() != model.getVersion()) {
                throw new OptimisticException("The model was previously changed");
            }
            base.increaseVersion();
            return memory.replace(model.getId(), base);
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public List<Base> getValues() {
        return new ArrayList<>(memory.values());
    }
}
