package dao;

import java.util.List;

/*
 * Interfaz genÃ©rica para operaciones CRUD (Crear, Leer, Actualizar, Eliminar).
 * Se usa con cualquier tipo de entidad (T), como Jugador, Item, etc.
 */

public interface DAO<T> {
    void insert(T entity);
    void update(T entity);
    void delete(Object id);
    T findById(Object id);
    List<T> findAll();
}