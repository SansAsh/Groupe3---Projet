package com.esieeit.projetsi.infrastructure.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implémentation in-memory du TaskRepository.
 * Utilisée temporairement en séance 3 — remplacée par JPA en séance 4.
 *
 * @Repository : Spring gère l'instance (injection automatique).
 */
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    // Stockage en mémoire : Map<id, Task>
    private final Map<Long, Task> store = new HashMap<>();

    // Séquence auto-incrémentée pour générer les IDs
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Task save(Task task) {
        // Si la tâche n'a pas encore d'ID, on lui en attribue un
        if (task.getId() == null) {
            long newId = sequence.incrementAndGet();
            task.setId(newId);
        }
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}