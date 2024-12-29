package org.expensetracker.db.exceptions;

/**
 * Conjunto de mensajes que son usados para explicar el motivo del lanzamiento 
 * de la excepción de un mal uso de las conexiones al servidor de BD.
 *
 * @author Anderson Acuña.
 */
public enum MySQLConnectionsMessages {
    TIMEOUT_EXPIRED("The timeout for connection expired."),
    FEATURE_NOT_SUPPORTED("The feature is not supported.");

    private final String message;

    MySQLConnectionsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
