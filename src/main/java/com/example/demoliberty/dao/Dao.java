package com.example.demoliberty.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.BadRequestException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class Dao<E, I>  {

    @PersistenceContext // Container managed persistence context
    protected EntityManager em;

    public List<E> getAll() {
        return em.createNamedQuery(typeSimple() + ".findAll", E()).getResultList();
    }

    public List<E> get(String q) {
        TypedQuery<E> namedQuery = em.createNamedQuery(typeSimple() + ".search", E());
        namedQuery.setParameter("q", "%" + q + "%");
        return namedQuery.getResultList();
    }

    public E getById(Long id) { return em.find(E(), id); }

    public E update(Long id, E e) {
        E found = em.find(E(), id);
        if (found == null) throw new BadRequestException("Entity with id " + id + " not found.");

//        e.setId(id);
        return em.merge(e);
    }

    public E add(E c) {
        em.persist(c);
        return c;
    }

    public boolean remove(Long id) {
        E e = em.find(E(), id);
        if (e == null) return false;

        em.remove(e);
        return true;
    }

    private String typeSimple() { return E().getSimpleName(); }

    private Class<E> E() {
        ParameterizedType thisDaoClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) thisDaoClass.getActualTypeArguments()[0];
    }

}
