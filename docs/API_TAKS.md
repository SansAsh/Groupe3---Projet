# API Tasks — Documentation endpoints

**Base URL :** `http://localhost:8080`

---

## Endpoints

### GET /api/tasks
Retourne la liste de toutes les tâches.

**Réponse :** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Initialiser le repo",
    "description": "Créer Gradle + README",
    "status": "TODO",
    "priority": "MEDIUM"
  }
]
```

---

### GET /api/tasks/{id}
Retourne une tâche par son ID.

**Réponse :** `200 OK`
```json
{
  "id": 1,
  "title": "Initialiser le repo",
  "description": "Créer Gradle + README",
  "status": "TODO",
  "priority": "MEDIUM"
}
```
**Erreur :** `404 Not Found` si l'ID n'existe pas.

---

### POST /api/tasks
Crée une nouvelle tâche.

**Body (JSON) :**
```json
{
  "title": "Initialiser le repo",
  "description": "Créer Gradle + README"
}
```
**Réponse :** `201 Created`
```json
{
  "id": 1,
  "title": "Initialiser le repo",
  "description": "Créer Gradle + README",
  "status": "TODO",
  "priority": "MEDIUM"
}
```

---

### PUT /api/tasks/{id}
Met à jour une tâche existante. Seuls les champs fournis sont modifiés.

**Body (JSON) :**
```json
{
  "title": "Repo + CI",
  "description": "Ajouter pipeline GitHub Actions",
  "status": "IN_PROGRESS"
}
```
**Réponse :** `200 OK` — tâche mise à jour.
**Erreur :** `404 Not Found` si l'ID n'existe pas.

**Valeurs possibles pour `status` :**
- `TODO`
- `IN_PROGRESS`
- `DONE`
- `ARCHIVED`

---

### DELETE /api/tasks/{id}
Supprime une tâche par son ID.

**Réponse :** `204 No Content`
**Erreur :** `404 Not Found` si l'ID n'existe pas.

---

## Exemples curl

```bash
# Créer une tâche
curl -i -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Initialiser le repo","description":"Créer Gradle + README"}'

# Lister toutes les tâches
curl -i http://localhost:8080/api/tasks

# Récupérer une tâche par ID
curl -i http://localhost:8080/api/tasks/1

# Mettre à jour une tâche
curl -i -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Repo + CI","status":"IN_PROGRESS"}'

# Supprimer une tâche
curl -i -X DELETE http://localhost:8080/api/tasks/1
```

---

## Codes HTTP

| Code | Signification         | Quand                        |
|------|-----------------------|------------------------------|
| 200  | OK                    | GET, PUT réussi              |
| 201  | Created               | POST réussi                  |
| 204  | No Content            | DELETE réussi                |
| 404  | Not Found             | ID inexistant                |
| 400  | Bad Request           | Données invalides (TP 3.2)   |
| 500  | Internal Server Error | Erreur non gérée             |