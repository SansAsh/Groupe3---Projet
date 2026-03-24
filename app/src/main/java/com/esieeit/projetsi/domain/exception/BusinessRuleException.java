package com.esieeit.projetsi.domain.exception;

/**
 * Exception levée lorsqu'une règle métier est violée
 * (transition de statut interdite, ownership, etc.).
 */
public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String message) {
        super(message);
    }
}