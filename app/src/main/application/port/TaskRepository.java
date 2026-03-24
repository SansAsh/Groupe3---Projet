package com.esieeit.projetsi.application.port;

import java.util.List;
import java.util.Optional;

/**
 * Interface (port) du repository de tâches.
 * Le service dépend de cette interface, pas de l'implémentation.
 * En séance 4, on remplacera InMemoryTaskRepository par JpaTaskRepository.
 */
public interface TaskRepository {

    /** Sauvegarde une tâche (création ou mise à jour). Retourne la tâche avec son ID. */
    Task save(Task task);

    /** Recherche une tâche par son ID. Retourne Optional.empty() si inexistante. */
    Optional<Task> findById(Long id);

    /** Retourne toutes les tâches. */
    List<Task> findAll();

    /** Supprime une tâche par son ID. */
    void deleteById(Long id);

    /** Vérifie si une tâche existe pour cet ID. */
    boolean existsById(Long id);
}