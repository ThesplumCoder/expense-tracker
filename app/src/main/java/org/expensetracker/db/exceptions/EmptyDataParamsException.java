package org.expensetracker.db.exceptions;

/**
 * Modela las excepciones que se pueden dar cuando se usan las clases que 
 * heredan de {@link db.DataStore}. En particular, cuando no se define alguno 
 * de los parámetros.
 *
 * @author Anderson Acuña.
 */
public final class EmptyDataParamsException extends Exception {

    /**
     * Crea una exepción para el caso en que no se suministra el nombre de la 
     * tabla.
     */
    public EmptyDataParamsException(DataExceptionMessages dem) {
        super(dem.getMessage());
    }
}
