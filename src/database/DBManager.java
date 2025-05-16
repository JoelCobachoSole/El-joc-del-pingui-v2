package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    // URLs de conexión: centro (VPN) y externa (casa)
    private static final String[] URLS = {
        "jdbc:oracle:thin:@//oracle.ilerna.com:1521/XEPDB2",
        "jdbc:oracle:thin:@//192.168.3.26:1521/XEPDB2"
    };

    private static final String USER = "DM2425_PIN_GRUP02";
    private static final String PASSWORD = "AACIS02";

    public static Connection connect() {
        Connection connection = null;

        try {
            // Cargar driver Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Oracle JDBC driver not found.");
            e.printStackTrace();
            return null;
        }

        for (String url : URLS) {
            try {
                connection = DriverManager.getConnection(url, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos: " + url);
                return connection;
            } catch (SQLException e) {
                System.err.println("⚠️ No se pudo conectar a: " + url);
                System.err.println("    Motivo: " + e.getMessage());
            }
        }

        System.err.println("Error: No se pudo conectar a ninguna base de datos.");
        return null;
    }

    public static void close(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }
}
