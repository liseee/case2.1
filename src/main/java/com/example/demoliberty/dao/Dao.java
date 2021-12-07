package com.example.demoliberty.dao;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import java.lang.reflect.ParameterizedType;

public abstract class Dao<E, I>  {

    @PersistenceContext // Container managed persistence context
    protected EntityManager em;

    public E getById(Long id) { return em.find(E(), id); }

    public E update(Long id, E e) {
        E found = em.find(E(), id);
        if (found == null) throw new BadRequestException("Entity with id " + id + " not found.");

//        e.setId(id);
        return em.merge(e);
    }

    private Class<E> E() {
        ParameterizedType thisDaoClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) thisDaoClass.getActualTypeArguments()[0];
    }

}
