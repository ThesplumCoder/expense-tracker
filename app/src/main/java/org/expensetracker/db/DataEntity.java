package org.expensetracker.db;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.expensetracker.db.exceptions.DataExceptionMessages;
import org.expensetracker.db.exceptions.EmptyDataParamsException;

public abstract class DataEntity implements Cloneable {
    private String tableName;

    protected DataEntity(String tableName) {
        if (tableName != null && !(tableName.isBlank())) {
            this.tableName = tableName;
        } else {
            Logger.getLogger(DataEntity.class.getName())
                .log(Level.WARNING, DataExceptionMessages.MISSING_TABLE_NAME.getMessage());
        }
    }

    protected void setTableName(String tableName) throws EmptyDataParamsException {
        if (tableName != null && !(tableName.isBlank())) {
            this.tableName = tableName;
        } else {
            throw new EmptyDataParamsException(DataExceptionMessages.MISSING_TABLE_NAME);
        }
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public abstract Object clone();
}
