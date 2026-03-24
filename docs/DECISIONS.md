# Décisions d'architecture (ADR) - Projet SI Java (ESIEE‑IT 2025-2026)

---

## D-01 : TaskStatus modélisé en enum (pas en entité)

**Contexte :** On doit représenter l'avancement d'une tâche.

**Décision :** `TaskStatus` est un **enum Java** (`TODO`, `IN_PROGRESS`, `DONE`, `ARCHIVED`).

**Justification :**
- Les valeurs sont fixes et connues à l'avance
- Un enum est plus simple, plus sûr et évite une table inutile en base
- Suffisant pour le MVP L2

**Alternative écartée :** entité `Status` avec table en base — trop complexe pour le niveau actuel.

---

## D-02 : UserRole modélisé en enum (pas en entité)

**Contexte :** On doit gérer les droits utilisateur (user / admin).

**Décision :** `UserRole` est un **enum** (`USER`, `ADMIN`).

**Justification :**
- Seulement 2 rôles dans le MVP
- Pas besoin d'une table `roles` séparée pour L2
- Peut évoluer vers une entité `Role` si les besoins augmentent

**Évolution possible :** table `roles` + relation `ManyToMany` si on ajoute des rôles métier fins.

---

## D-03 : Pas de ManyToMany au départ

**Contexte :** La relation "membres d'un projet" pourrait être ManyToMany (User ↔ Project).

**Décision :** On **évite ManyToMany** pour le MVP. Un projet a un seul owner (User).

**Justification :**
- ManyToMany est source d'erreurs en JPA (tables de jointure, cycles)
- Le MVP ne demande pas de collaboration multi-utilisateur sur un projet
- Simplicité et robustesse en priorité

**Évolution possible :** ajouter une entité `ProjectMember` avec rôle (OWNER, CONTRIBUTOR) si besoin.

---

## D-04 : Utilisation de `Instant` pour les dates d'audit

**Contexte :** On doit stocker `createdAt` et `updatedAt` sur les entités.

**Décision :** On utilise **`Instant`** (UTC) pour tous les champs d'audit.

**Justification :**
- `Instant` est indépendant du fuseau horaire (meilleure pratique pour les APIs)
- Compatible JPA et Spring Data
- `LocalDateTime` sans fuseau peut poser des problèmes en déploiement

**Exception :** `dueDate` sur Task utilise `LocalDate` (date métier sans heure).

---

## D-05 : Les DTOs sont séparés des entités domain

**Contexte :** Les controllers reçoivent et retournent des données JSON.

**Décision :** On crée des **DTOs distincts** (`request` / `response`) dans `api/dto/`, séparés des entités `domain/model`.

**Justification :**
- Évite d'exposer des champs internes (ex: `passwordHash`)
- Permet de faire évoluer le modèle interne sans casser l'API
- Bonne pratique en entreprise (contrat API stable)

---

## D-06 : Le domain ne dépend pas de Spring

**Contexte :** Les entités métier pourraient être annotées avec `@Entity`, `@Column`, etc.

**Décision :** Les classes dans `domain/model` sont des **POJO Java purs**, sans annotations Spring ni JPA.

**Justification :**
- Le domain reste testable unitairement sans Spring context
- Séparation claire entre logique métier et persistance
- Aligné avec les principes Clean Architecture

**Note :** Les annotations JPA seront ajoutées dans `infrastructure/persistence` en séance 4.

---

## D-07 : Les exceptions métier sont dans domain/exception

**Contexte :** On doit gérer les erreurs (ressource introuvable, transition invalide, etc.).

**Décision :** Les exceptions personnalisées sont dans `domain/exception` :
- `ResourceNotFoundException` → 404
- `InvalidStatusTransitionException` → 400
- `UnauthorizedException` → 403

**Justification :**
- Les règles d'erreur sont des règles métier
- Centralisées et réutilisables dans tous les services