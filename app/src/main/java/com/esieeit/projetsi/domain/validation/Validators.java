package com.esieeit.projetsi.domain.validation;

import java.util.regex.Pattern;

/**
 * Classe utilitaire centralisée pour les validations techniques.
 * Non instanciable — utiliser les méthodes statiques.
 */
public final class Validators {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private Validators() {
        // Empêcher l'instanciation
    }

    /**
     * Vérifie qu'une valeur n'est pas null.
     *
     * @param value     la valeur à tester
     * @param fieldName le nom du champ (pour le message d'erreur)
     */
    public static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " ne doit pas être null");
        }
    }

    /**
     * Vérifie qu'une chaîne n'est pas null, vide, et respecte les tailles min/max.
     *
     * @param value     la valeur à tester
     * @param fieldName le nom du champ
     * @param min       longueur minimale (incluse)
     * @param max       longueur maximale (incluse)
     * @return la valeur trimmée si valide
     */
    public static String requireNonBlank(String value, String fieldName, int min, int max) {
        if (value == null) {
            throw new ValidationException(fieldName + " ne doit pas être null");
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new ValidationException(fieldName + " ne doit pas être vide");
        }
        return requireSize(trimmed, fieldName, min, max);
    }

    /**
     * Vérifie que la longueur d'une chaîne est dans [min, max].
     *
     * @param value     la chaîne à tester (non null)
     * @param fieldName le nom du champ
     * @param min       longueur minimale (incluse)
     * @param max       longueur maximale (incluse)
     * @return la valeur si valide
     */
    public static String requireSize(String value, String fieldName, int min, int max) {
        int len = value.length();
        if (len < min || len > max) {
            throw new ValidationException(
                    fieldName + " doit contenir entre " + min + " et " + max + " caractères (actuel : " + len + ")"
            );
        }
        return value;
    }

    /**
     * Vérifie qu'une chaîne est un email valide.
     *
     * @param value     la valeur à tester
     * @param fieldName le nom du champ
     * @return la valeur trimmée si valide
     */
    public static String requireEmail(String value, String fieldName) {
        String trimmed = requireNonBlank(value, fieldName, 5, 254);
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new ValidationException(fieldName + " doit être un email valide (ex: alice@example.com)");
        }
        return trimmed;
    }

    /**
     * Vérifie qu'un Long est positif (> 0).
     *
     * @param value     la valeur à tester
     * @param fieldName le nom du champ
     * @return la valeur si valide
     */
    public static Long requirePositive(Long value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value <= 0) {
            throw new ValidationException(fieldName + " doit être > 0 (actuel : " + value + ")");
        }
        return value;
    }
}