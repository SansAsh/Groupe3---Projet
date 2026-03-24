package com.esieeit.projetsi.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.ValidationException;
import com.esieeit.projetsi.domain.validation.Validators;
/**
 * Entité métier représentant un utilisateur de la plateforme.
 * POJO pur — pas d'annotations Spring ou JPA (ajoutées en séance 4).
 */
public class User {

    private Long id;
    private String email;
    private String username;
    private String passwordHash; // obligatoire en séance 5 (JWT)
    private Set<UserRole> roles;

    // ─────────────────────────────────────────────────────────
    // Constructeur
    // ─────────────────────────────────────────────────────────

    /**
     * Crée un utilisateur avec validation immédiate.
     *
     * @param email    email valide et unique
     * @param username nom d'utilisateur (3 à 30 caractères)
     * @param roles    ensemble de rôles (au moins 1)
     */
    public User(String email, String username, Set<UserRole> roles) {
        setEmail(email);
        setUsername(username);
        setRoles(roles);
    }

    // ─────────────────────────────────────────────────────────
    // Getters / Setters
    // ─────────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    /** Utilisé uniquement par la couche infrastructure (DB). */
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = Validators.requireEmail(email, "email");
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = Validators.requireNonBlank(username, "username", 3, 30);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Accepte null pour l'instant (auth ajoutée en séance 5).
     * Si non null, impose une longueur minimale de 10 caractères (hash bcrypt).
     */
    public void setPasswordHash(String passwordHash) {
        if (passwordHash != null) {
            Validators.requireSize(passwordHash, "passwordHash", 10, 255);
        }
        this.passwordHash = passwordHash;
    }

    /** Retourne une vue non modifiable des rôles. */
    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public final void setRoles(Set<UserRole> roles) {
        Validators.requireNonNull(roles, "roles");
        if (roles.isEmpty()) {
            throw new ValidationException("roles doit contenir au moins un rôle");
        }
        this.roles = new HashSet<>(roles);
    }

    // ─────────────────────────────────────────────────────────
    // Méthodes métier
    // ─────────────────────────────────────────────────────────

    /**
     * Vérifie si l'utilisateur possède un rôle donné.
     *
     * @param role le rôle à tester
     * @return true si l'utilisateur possède ce rôle
     */
    public boolean hasRole(UserRole role) {
        Validators.requireNonNull(role, "role");
        return roles.contains(role);
    }

    /**
     * Ajoute un rôle à l'utilisateur.
     *
     * @param role le rôle à ajouter
     */
    public void addRole(UserRole role) {
        Validators.requireNonNull(role, "role");
        this.roles.add(role);
    }

    // ─────────────────────────────────────────────────────────
    // equals / hashCode / toString
    // ─────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + "', username='" + username + "', roles=" + roles + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}