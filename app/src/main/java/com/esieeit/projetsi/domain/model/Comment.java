package com.esieeit.projetsi.domain.model;

import java.util.Objects;

import com.esieeit.projetsi.domain.validation.Validators;

/**
 * Entité métier représentant un commentaire posté sur une tâche.
 * POJO pur — pas d'annotations Spring ou JPA.
 */
public class Comment {

    private Long id;
    private String content;
    private Task task;
    private User author;

    // ─────────────────────────────────────────────────────────
    // Constructeur
    // ─────────────────────────────────────────────────────────

    /**
     * Crée un commentaire avec validation immédiate.
     *
     * @param content contenu du commentaire (1 à 500 caractères, obligatoire)
     * @param task    tâche associée (obligatoire)
     * @param author  auteur du commentaire (obligatoire)
     */
    public Comment(String content, Task task, User author) {
        setContent(content);
        setTask(task);
        setAuthor(author);
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

    public String getContent() {
        return content;
    }

    public final void setContent(String content) {
        this.content = Validators.requireNonBlank(content, "comment.content", 1, 500);
    }

    public Task getTask() {
        return task;
    }

    public final void setTask(Task task) {
        Validators.requireNonNull(task, "comment.task");
        this.task = task;
    }

    public User getAuthor() {
        return author;
    }

    public final void setAuthor(User author) {
        Validators.requireNonNull(author, "comment.author");
        this.author = author;
    }

    // ─────────────────────────────────────────────────────────
    // equals / hashCode / toString
    // ─────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Comment{id=" + id
                + ", author='" + (author != null ? author.getUsername() : "null") + "'"
                + ", task='" + (task != null ? task.getTitle() : "null") + "'"
                + ", content='" + content + "'"
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}