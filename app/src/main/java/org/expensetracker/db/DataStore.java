package org.expensetracker.db;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import org.expensetracker.db.exceptions.DataExceptionMessages;
import org.expensetracker.db.exceptions.EmptyDataParamsException;
import org.expensetracker.db.exceptions.MySQLConnectionsMessages;

public abstract class DataStore<T extends DataEntity> {
    private static final String CLASS_NAME = DataStore.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    private MySQLConnection connection;

    protected DataStore(MySQLConnection connection) {
        if (connection != null) {
            this.connection = connection;
        }
    }

    protected void setConnection(MySQLConnection connection) throws EmptyDataParamsException {
        if (connection != null) {
            this.connection = connection;
        } else {
            throw new EmptyDataParamsException(DataExceptionMessages.MISSING_CONNECTION);
        }
    }

    protected MySQLConnection getConnection() throws EmptyDataParamsException {
        MySQLConnection conn;

        conn = null;
        if (this.connection != null) {
            conn = this.connection;
        } else {
            throw new EmptyDataParamsException(DataExceptionMessages.MISSING_CONNECTION);
        }

        return conn;
    }

    public List<T> findAll(T dataEntity) {
        ArrayList<T> registers;
        Class<T> classT;
        Field[] fields;
        DataEntity de = dataEntity;
        String query;

        registers = new ArrayList<>();
        classT = (Class<T>) dataEntity.getClass();
        fields = classT.getDeclaredFields();
        query = "SELECT * FROM " + de.getTableName();
        try (
            Statement s = connection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = s.executeQuery(query);        
        ) {
            if (result.first()) {
                do { 
                    String pascalField;
                    T register = classT.cast(dataEntity.clone());
                    for(Field field: fields) {
                        pascalField = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1, field.getName().length());
                        Method method = classT.getDeclaredMethod("set" + pascalField, field.getType());
                        method.invoke(register, result.getObject(pascalField, field.getType()));
                    }
                    registers.add(register);
                } while(result.next());
            }
        } catch (IllegalAccessException iae) {
            DataStore.LOGGER
                .log(Level.SEVERE, "A setter method in " + classT.getName() + " cannot accesible.", iae);
        } catch (InvocationTargetException ite) {
            DataStore.LOGGER
                .log(Level.SEVERE, "A setter method in " + classT.getName() + " throws an exception.", ite);
        } catch (NoSuchMethodException nsme) {
            DataStore.LOGGER
                .log(Level.SEVERE, "A field in " + classT.getName() + " doesn't have its setter method.", nsme);
        } catch (SQLTimeoutException ste) {
            DataStore.LOGGER
                .log(Level.WARNING, MySQLConnectionsMessages.TIMEOUT_EXPIRED.getMessage(), ste);
        } catch (SQLException se) {
            DataStore.LOGGER
                .log(Level.WARNING, DataExceptionMessages.GENERAL_DB_ERROR.getMessage(), se);
        }
        return registers;
    }
}
