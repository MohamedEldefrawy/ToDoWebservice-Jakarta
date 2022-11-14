package com.todo.repositroy;

import java.util.List;

public interface Repository<T> {
    boolean create(T entity);

    boolean update(T entity);

    boolean delete(Integer id);

    List<T> get();

    T getById(Integer id);
}
