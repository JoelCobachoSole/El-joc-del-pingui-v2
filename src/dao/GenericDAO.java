package dao;

import database.DBManager;

import java.sql.*;
import java.util.*;

public abstract class GenericDAO<T> implements DAO<T> {

    protected String tableName;
    protected String primaryKey;

    public GenericDAO(String tableName, String primaryKey) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract Map<String, Object> getFields(T entity);

    @Override
    public void insert(T entity) {
        Map<String, Object> fields = getFields(entity);
        String columns = String.join(", ", fields.keySet());
        String placeholders = String.join(", ", Collections.nCopies(fields.size(), "?"));
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object value : fields.values()) {
                stmt.setObject(index++, value);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity) {
        Map<String, Object> fields = getFields(entity);
        Object idValue = fields.remove(primaryKey);

        String setClause = String.join(" = ?, ", fields.keySet()) + " = ?";
        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKey + " = ?";

        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object value : fields.values()) {
                stmt.setObject(index++, value);
            }
            stmt.setObject(index, idValue);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = ?";
        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(Object id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DBManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
