package dao;

import modelo.JugadorDB;
import database.DBManager;

import java.sql.*;

public class JugadorDAO {

    public JugadorDB findByNombre(String nombre) {
        String sql = "SELECT * FROM jugadores WHERE nombre = ?";
        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new JugadorDB(
                        rs.getInt("posicion"),
                        rs.getString("nombre"),
                        rs.getString("color"),
                        rs.getString("password"),
                        rs.getInt("nivel"),
                        null // No se carga partida desde la DB, se usa binario
                );
            }

        } catch (SQLException e) {
            System.err.println("Error en findByNombre(): " + e.getMessage());
        }
        return null;
    }

    public boolean insertNewUser(JugadorDB jugador) {
        String sql = "INSERT INTO jugadores (posicion, nombre, color, password, nivel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jugador.getPosicion());
            stmt.setString(2, jugador.getNombre());
            stmt.setString(3, jugador.getColor());
            stmt.setString(4, jugador.getPassword());
            stmt.setInt(5, jugador.getNivel());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en insertNewUser(): " + e.getMessage());
            return false;
        }
    }

    // (Opcional futuro) Para actualizar el estado de jugador si decides subir partidas a DB
    public boolean updateJugador(JugadorDB jugador) {
        String sql = "UPDATE jugadores SET posicion = ?, color = ?, password = ?, nivel = ? WHERE nombre = ?";
        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jugador.getPosicion());
            stmt.setString(2, jugador.getColor());
            stmt.setString(3, jugador.getPassword());
            stmt.setInt(4, jugador.getNivel());
            stmt.setString(5, jugador.getNombre());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error en updateJugador(): " + e.getMessage());
            return false;
        }
    }
}