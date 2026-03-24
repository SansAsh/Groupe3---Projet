package com.esieeit.projetsi.api.dto;

/**
 * DTO reçu lors de la mise à jour d'une tâche (PUT /api/tasks/{id}).
 * Tous les champs sont optionnels : seuls les champs non-null sont mis à jour.
 */
public class TaskUpdateRequest {

    private String title;
    private String description;
    private String status; // optionnel : "TODO", "IN_PROGRESS", "DONE", "ARCHIVED"

    public TaskUpdateRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}