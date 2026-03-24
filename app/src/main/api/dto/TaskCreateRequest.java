package com.esieeit.projetsi.api.dto;

/**
 * DTO reçu lors de la création d'une tâche (POST /api/tasks).
 * Ne contient que les champs que le client peut envoyer.
 */
public class TaskCreateRequest {

    private String title;
    private String description;

    // Constructeur vide obligatoire pour Jackson (désérialisation JSON)
    public TaskCreateRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}