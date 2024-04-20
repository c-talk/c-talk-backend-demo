package me.a632079.ctalk.service;

import java.util.List;

public interface MongoRepository<T> {
    T findById(Long id);
    List<T> findAll();
    boolean save(T t);
    boolean saveAll(List<T> ts);
    boolean deleteById(Long id);
    boolean deleteAll();
}
