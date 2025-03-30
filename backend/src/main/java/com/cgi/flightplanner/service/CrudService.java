package com.cgi.flightplanner.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CrudService<T, R extends JpaRepository<T, Long>> {
    protected final R repository;

    protected CrudService(R repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }
}
