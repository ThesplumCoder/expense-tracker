package org.expensetracker.db;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.expensetracker.db.exceptions.DataExceptionMessages;

/**
 * Especificación de la funcionalidad que todas las conexiones a bases de datos 
 * tienen.
 *
 * @author Anderson Acuña.
 */
public class MySQLConnection implements AutoCloseable {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_PROTOCOL = "jdbc:mysql:";
    private static final String CLASS_NAME = MySQLConnection.class.getName();
    private static final String LOAD_BALANCING_PROTOCOL = "jdbc:mysql:loadbalance";
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    private static boolean DB_DRIVER_LOADED = false;
    private static String hostname = "localhost";
    private static String port = "3306";

    private Connection con;
    private boolean useConnection = false;

    /**
     * Construye una conexión con la base de datos MySQL usando los valores 
     * especificados.
     *
     * @param url Dirección URL conforme al estándar JDBC para indicar en donde 
     * está respondiendo el servidor de base de datos.
     * @param username Nombre de usuario para iniciar sesión en el servidor de 
     * BBDD.
     * @param password Contraseña del usuario para iniciar sesión en el servidor 
     * de BBDD.
     */
    public MySQLConnection(String url, String username, String password) {
        if(!DB_DRIVER_LOADED) {
            loadDriver();
        }

        try {
            if (DB_DRIVER_LOADED) {
                con = DriverManager.getConnection(url, username, password);
                con.setTransactionIsolation(8);
            }
        } catch (SQLException sqlEx) {
            MySQLConnection.LOGGER
                .log(Level.SEVERE, DataExceptionMessages.CANNOT_CONNECT.getMessage());
        }
    }

    /**
     * Crea una conexión a un servidor de MySQL, usando los parámetros 
     * suministrados.
     *
     * @param host Dominio o dirección IPv4 donde se encuentra el servidor.
     * @param user Nombre de usuario para iniciar sesión en el servidor de 
     * BBDD.
     * @param password Contraseña del usuario para iniciar sesión en el servidor 
     * de BBDD.
     * @param database Nombre de la BD específica sobre la que se realizarán 
     * las operaciones.
     */
    public MySQLConnection(String host, String user, String password, String database) {
        String dbUrl;

        if(!DB_DRIVER_LOADED) {
            loadDriver();
        }

        dbUrl = MySQLConnection.DEFAULT_PROTOCOL + "//" + host + "/" + database;
        try {
            if (DB_DRIVER_LOADED) {
                con = DriverManager.getConnection(dbUrl, user, password);
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            }
        } catch (SQLException sqlEx) {
            MySQLConnection.LOGGER
                .log(Level.SEVERE, DataExceptionMessages.CANNOT_CONNECT.getMessage());
        }
    }

    /**
     * Carga el controlador que proporciona el conector de MySQL. Si ya está 
     * cargado no lo vuelve a cargar.
     */
    private void loadDriver() {
        if (!DB_DRIVER_LOADED) {
            try {
                Class.forName(DB_DRIVER);
                MySQLConnection.DB_DRIVER_LOADED = true;
            } catch (ClassNotFoundException cnfEx) {
                MySQLConnection.LOGGER
                    .log(Level.SEVERE, DataExceptionMessages.MISSING_DRIVER_CLASS.getMessage(), cnfEx);
            }
        }
    }

    /**
     * Retorna si la conexión subyacente está definida o no
     */
    private boolean hasConnection() {
        return !(con == null);
    }

    /**
     * Retorna la conexión subyacente.
     */
    public Connection getConnection() {
        Connection conn;

        conn = null;
        conn = (hasConnection())? this.con : null;

        return conn;
    }

    /**
     * Obtiene el estado de la conexión.
     *
     * @return {@code true} si la conexión está en uso, de lo contrario {@code false}.
     */
    public boolean getUseConnection() {
        return useConnection;
    }

    /**
     * Cambia el estado de la conexión.
     *
     * Si la conexión está libre, la cambiará al estado que indica que está en 
     * uso.
     */
    public void changeUseConnection() {
        this.useConnection = !useConnection;
    }
    
    /**
     * Cierra la conexión interna en la que se basa la clase.
     */
    @Override
    public void close() {
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException sqlEx) {
            MySQLConnection.LOGGER
                .log(Level.WARNING, DataExceptionMessages.GENERAL_DB_ERROR.getMessage(), sqlEx);
        }
    }
}
