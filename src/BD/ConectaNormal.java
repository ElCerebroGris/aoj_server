/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zamba
 */
public class ConectaNormal {
    
    private static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/online";
    private static String user = "postgres";
    private static String password = "zamba23";
    private static Connection connection = null;

    /**
     * getConnection
     *
     * @param driver
     * @param url
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String driver, String url, String user, 
            String password) throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() throws SQLException {
        return getConnection(driver, url, user, password);

    }

    public static Connection getConnection(String u, String p) throws SQLException {
        return getConnection(driver, url, u, p);

    }
    public static void close() throws SQLException {
        connection.close();
    }

    /**
     * getConnection
     *
     * @param host
     * @param port
     * @param nameBD
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String host, String port, String nameBD) throws SQLException {
        url = "jdbc:postgresql://" + host + ":" + port + "/" + nameBD;

        return getConnection(driver, url, user, password);
    }
    public static Connection getConnection(String host, String port, String nameBD, 
            String user, String pass) throws SQLException {
        url = "jdbc:postgresql://" + host + ":" + port + "/" + nameBD;

        return getConnection(driver, url, user, pass);
    }
    
}
