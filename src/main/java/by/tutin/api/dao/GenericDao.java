package by.tutin.api.dao;

import java.util.List;

public interface GenericDao<T>{
    void save(T entity);
    T getById(Long id);
    List<T> getAll();
    void delete(T entity);
    T update(T entity);
}
