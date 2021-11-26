package com.the.hugging.team.repositories;

import java.util.List;
import java.util.Optional;

public interface ObjectRepository<T> {

    void save(T obj);

    void update(T obj);

    void delete(T obj);

    Optional<T> getById(int id);

    List<T> getAll();
}
