package org.expensetracker.db.exceptions;

/**
 * Conjunto de mensajes que son usados para explicar el motivo del lanzamiento 
 * de la excepción de un mal uso de los almacenes de datos.
 *
 * @author Anderson Acuña.
 */
public enum DataExceptionMessages {
    MISSING_CONNECTION("The connection is missing."),
    MISSING_DRIVER_CLASS("The driver class didn't load."),
    MISSING_TABLE_NAME("The table name is missing."),
    CANNOT_CONNECT("The connection was not made."),
    GENERAL_DB_ERROR("An error in database access occured.");

    private final String message;

    DataExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
