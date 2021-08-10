package by.tutin.api.service;

import java.util.List;

public interface GenericService<T>{
    void add(T entity);
    T getById(Long id);
    List<T> getAll();
    T update(T entity);
    void delete(T entity);
}
