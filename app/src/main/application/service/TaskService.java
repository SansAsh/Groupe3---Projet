package com.esieeit.projetsi.application.service;

import java.util.List;

/**
 * Service applicatif pour la gestion des tâches.
 * Contient la logique métier — le controller ne fait qu'orchestrer HTTP.
 *
 * @Service : Spring gère l'instance et l'injection.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Injection par constructeur (recommandé)
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Crée une nouvelle tâche à partir du DTO de création.
     * Note séance 3 : Task domain accepte un project null (simplifié).
     */
    public Task create(TaskCreateRequest req) {
        Validators.requireNonNull(req, "request");

        // Création de l'entité domain (project = null accepté en séance 3)
        // En séance 4+ on injectera le vrai Project via ProjectRepository
        Task task = new Task(req.getTitle(), req.getDescription(), null);

        return taskRepository.save(task);
    }

    /**
     * Retourne toutes les tâches.
     */
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    /**
     * Retourne une tâche par son ID.
     *
     * @throws BusinessRuleException si la tâche n'existe pas (404 plus tard)
     */
    public Task getById(Long id) {
        Validators.requireNonNull(id, "id");
        return taskRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Tâche introuvable : id=" + id));
    }

    /**
     * Met à jour les champs d'une tâche existante.
     * Seuls les champs non-null du DTO sont appliqués.
     */
    public Task update(Long id, TaskUpdateRequest req) {
        Validators.requireNonNull(id, "id");
        Validators.requireNonNull(req, "request");

        Task task = getById(id);

        if (req.getTitle() != null) {
            task.setTitle(req.getTitle());
        }
        if (req.getDescription() != null) {
            task.setDescription(req.getDescription());
        }
        // Changement de statut via les méthodes métier (workflow contrôlé)
        if (req.getStatus() != null) {
            TaskStatus newStatus = TaskStatus.valueOf(req.getStatus().toUpperCase());
            applyStatusTransition(task, newStatus);
        }

        return taskRepository.save(task);
    }

    /**
     * Supprime une tâche par son ID.
     *
     * @throws BusinessRuleException si la tâche n'existe pas
     */
    public void delete(Long id) {
        Validators.requireNonNull(id, "id");
        if (!taskRepository.existsById(id)) {
            throw new BusinessRuleException("Tâche introuvable : id=" + id);
        }
        taskRepository.deleteById(id);
    }

    // ── Helper privé : applique la bonne méthode métier selon le statut cible ──

    private void applyStatusTransition(Task task, TaskStatus target) {
        switch (target) {
            case IN_PROGRESS -> task.start();
            case DONE        -> task.complete();
            case ARCHIVED    -> task.archive();
            case TODO        -> task.reopen();
            default -> throw new BusinessRuleException("Statut inconnu : " + target);
        }
    }
}