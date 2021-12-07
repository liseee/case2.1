package com.example.demoliberty.dao;

import com.example.demoliberty.models.Role;
import com.example.demoliberty.models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
public class RoleDao {

    @PersistenceContext
    private EntityManager em;

    public Role findByName(String roleName){
        TypedQuery<Role> findByName = em.createQuery("SELECT r FROM Role r WHERE UPPER(r.name) = :name", Role.class);
        findByName.setParameter("name", roleName);
        return findByName.getSingleResult();
    }

    public List<Role> readAllRoles() {
        return em.createNamedQuery("Role.findAll", Role.class).getResultList();
    }
}
