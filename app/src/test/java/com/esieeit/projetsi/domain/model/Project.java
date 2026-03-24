package com.esieeit.projetsi.domain.model;

import java.util.Objects;

import com.esieeit.projetsi.domain.validation.Validators;

/**
 * Entité métier représentant un projet appartenant à un utilisateur.
 * POJO pur — pas d'annotations Spring ou JPA.
 */
public class Project {

    private Long id;
    private String name;
    private String description;
    private User owner;

    // ─────────────────────────────────────────────────────────
    // Constructeur
    // ─────────────────────────────────────────────────────────

    /**
     * Crée un projet avec validation immédiate.
     *
     * @param name        nom du projet (1 à 80 caractères, obligatoire)
     * @param description description optionnelle (null ou 0 à 500 caractères)
     * @param owner       propriétaire du projet (obligatoire)
     */
    public Project(String name, String description, User owner) {
        setName(name);
        setDescription(description);
        setOwner(owner);
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

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = Validators.requireNonBlank(name, "project.name", 1, 80);
    }

    public String getDescription() {
        return description;
    }

    /**
     * Description optionnelle. Si null ou vide après trim, stocke null.
     */
    public final void setDescription(String description) {
        if (description == null) {
            this.description = null;
            return;
        }
        String trimmed = description.trim();
        if (trimmed.isEmpty()) {
            this.description = null;
            return;
        }
        this.description = Validators.requireSize(trimmed, "project.description", 1, 500);
    }

    public User getOwner() {
        return owner;
    }

    public final void setOwner(User owner) {
        Validators.requireNonNull(owner, "project.owner");
        this.owner = owner;
    }

    // ─────────────────────────────────────────────────────────
    // Méthodes métier
    // ─────────────────────────────────────────────────────────

    /**
     * Renomme le projet.
     *
     * @param newName nouveau nom (1 à 80 caractères)
     */
    public void rename(String newName) {
        setName(newName);
    }

    /**
     * Vérifie si l'utilisateur donné est le propriétaire du projet.
     *
     * @param user l'utilisateur à tester
     * @return true si c'est le owner
     */
    public boolean isOwnedBy(User user) {
        Validators.requireNonNull(user, "user");
        return Objects.equals(this.owner.getId(), user.getId());
    }

    // ─────────────────────────────────────────────────────────
    // equals / hashCode / toString
    // ─────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + name + "', owner=" + owner.getUsername() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}