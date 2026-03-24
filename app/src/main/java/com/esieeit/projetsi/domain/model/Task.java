package com.esieeit.projetsi.domain.model;

import java.util.Objects;

import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.validation.Validators;

/**
 * Entité métier représentant une tâche appartenant à un projet.
 * Contient le workflow de statut avec transitions contrôlées.
 * POJO pur — pas d'annotations Spring ou JPA.
 */
public class Task {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Project project;
    private User assignee;

    // ─────────────────────────────────────────────────────────
    // Constructeur
    // ─────────────────────────────────────────────────────────

    /**
     * Crée une tâche avec le statut initial TODO.
     *
     * @param title       titre de la tâche (1 à 120 caractères, obligatoire)
     * @param description description optionnelle (null ou 0 à 1000 caractères)
     * @param project     projet auquel appartient la tâche (obligatoire)
     */
    public Task(String title, String description, Project project) {
        setTitle(title);
        setDescription(description);
        setProject(project);
        this.status = TaskStatus.TODO;         // statut initial obligatoire
        this.priority = TaskPriority.MEDIUM;   // priorité par défaut
    }

    // ─────────────────────────────────────────────────────────
    // Getters / Setters
    // ─────────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = Validators.requireNonBlank(title, "task.title", 1, 120);
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        if (description == null) {
            this.description = null;
            return;
        }
        String trimmed = description.trim();
        this.description = trimmed.isEmpty() ? null : Validators.requireSize(trimmed, "task.description", 1, 1000);
    }

    /** Le statut ne se modifie que via les méthodes métier (start, complete, archive). */
    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        Validators.requireNonNull(priority, "task.priority");
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public final void setProject(Project project) {
        Validators.requireNonNull(project, "task.project");
        this.project = project;
    }

    public User getAssignee() {
        return assignee;
    }

    // ─────────────────────────────────────────────────────────
    // Méthodes métier — gestion de l'assignee
    // ─────────────────────────────────────────────────────────

    /**
     * Assigne la tâche à un utilisateur.
     *
     * @param user l'utilisateur assigné (obligatoire)
     */
    public void assignTo(User user) {
        Validators.requireNonNull(user, "task.assignee");
        this.assignee = user;
    }

    /** Désassigne la tâche (remet assignee à null). */
    public void unassign() {
        this.assignee = null;
    }

    // ─────────────────────────────────────────────────────────
    // Méthodes métier — workflow de statut
    // ─────────────────────────────────────────────────────────

    /**
     * Démarre la tâche : TODO → IN_PROGRESS.
     *
     * @throws BusinessRuleException si le statut actuel n'est pas TODO
     */
    public void start() {
        if (status != TaskStatus.TODO) {
            throw new BusinessRuleException(
                    "Transition interdite : start() est possible uniquement depuis TODO (statut actuel : " + status + ")"
            );
        }
        this.status = TaskStatus.IN_PROGRESS;
    }

    /**
     * Termine la tâche : IN_PROGRESS → DONE.
     *
     * @throws BusinessRuleException si le statut actuel n'est pas IN_PROGRESS
     */
    public void complete() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw new BusinessRuleException(
                    "Transition interdite : complete() est possible uniquement depuis IN_PROGRESS (statut actuel : " + status + ")"
            );
        }
        this.status = TaskStatus.DONE;
    }

    /**
     * Archive la tâche : DONE → ARCHIVED.
     *
     * @throws BusinessRuleException si le statut actuel n'est pas DONE
     */
    public void archive() {
        if (status != TaskStatus.DONE) {
            throw new BusinessRuleException(
                    "Transition interdite : archive() est possible uniquement depuis DONE (statut actuel : " + status + ")"
            );
        }
        this.status = TaskStatus.ARCHIVED;
    }

    /**
     * Remet la tâche en TODO depuis IN_PROGRESS (optionnel).
     *
     * @throws BusinessRuleException si le statut actuel n'est pas IN_PROGRESS
     */
    public void reopen() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw new BusinessRuleException(
                    "Transition interdite : reopen() est possible uniquement depuis IN_PROGRESS (statut actuel : " + status + ")"
            );
        }
        this.status = TaskStatus.TODO;
    }

    // ─────────────────────────────────────────────────────────
    // equals / hashCode / toString
    // ─────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Task{id=" + id
                + ", title='" + title + "'"
                + ", status=" + status
                + ", priority=" + priority
                + ", project='" + (project != null ? project.getName() : "null") + "'"
                + ", assignee=" + (assignee != null ? assignee.getUsername() : "none")
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}