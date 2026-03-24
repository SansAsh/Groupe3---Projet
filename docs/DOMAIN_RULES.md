# Règles du domaine métier — DOMAIN_RULES.md

---

## Validations par entité

### User
| Champ        | Règle                                        |
|--------------|----------------------------------------------|
| email        | Obligatoire, format valide, 5 à 254 chars    |
| username     | Obligatoire, 3 à 30 caractères               |
| passwordHash | Optionnel pour l'instant (séance 5), min 10 chars si présent |
| roles        | Obligatoire, au moins 1 rôle                 |

### Project
| Champ       | Règle                                         |
|-------------|-----------------------------------------------|
| name        | Obligatoire, 1 à 80 caractères                |
| description | Optionnel, max 500 caractères                 |
| owner       | Obligatoire, référence User non null          |

### Task
| Champ       | Règle                                         |
|-------------|-----------------------------------------------|
| title       | Obligatoire, 1 à 120 caractères               |
| description | Optionnel, max 1000 caractères                |
| status      | Obligatoire, initialié à TODO à la création   |
| priority    | Obligatoire, défaut MEDIUM                    |
| project     | Obligatoire, référence Project non null       |
| assignee    | Optionnel                                     |

### Comment
| Champ   | Règle                                           |
|---------|-------------------------------------------------|
| content | Obligatoire, 1 à 500 caractères                 |
| task    | Obligatoire, référence Task non null            |
| author  | Obligatoire, référence User non null            |

---

## Workflow de statut (TaskStatus)

### Transitions autorisées
| De            | Vers          | Méthode    |
|---------------|---------------|------------|
| TODO          | IN_PROGRESS   | start()    |
| IN_PROGRESS   | DONE          | complete() |
| DONE          | ARCHIVED      | archive()  |
| IN_PROGRESS   | TODO          | reopen()   |

### Transitions interdites
| De         | Vers       | Raison                          |
|------------|------------|---------------------------------|
| TODO       | DONE       | On doit passer par IN_PROGRESS  |
| TODO       | ARCHIVED   | Interdit directement            |
| DONE       | TODO       | Interdit (utiliser reopen avant)|
| ARCHIVED   | tout       | État final — lecture seule      |

### Message d'erreur
> `"Transition interdite : <méthode>() est possible uniquement depuis <STATUT> (statut actuel : <STATUT>)"`

---

## Règles d'autorisation (aperçu — détail séance 5)

- Seul le **owner** d'un projet peut le modifier ou le supprimer
- Seul l'**auteur** d'un commentaire peut le supprimer
- Un **Admin** peut tout lire/modifier/supprimer
- Toute requête sans auth → `401 Unauthorized`
- Accès à la ressource d'un autre user → `403 Forbidden`

---

## Décisions d'implémentation

| Décision | Justification |
|----------|---------------|
| `TaskStatus` en enum | Valeurs fixes connues à l'avance — pas besoin de table |
| `UserRole` en enum | Seulement 2 rôles pour le MVP — peut évoluer |
| `archive()` depuis DONE uniquement | Éviter l'archivage de tâches non terminées |
| `passwordHash` optionnel (null OK) | Auth ajoutée en séance 5, champ prévu maintenant |
| Pas de Spring dans domain | Domain testable sans contexte Spring |
| Validators centralisé | Évite la duplication de code de validation |