package com.example.demoliberty.dao;

import com.example.demoliberty.models.Team;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
public class TeamDao extends Dao<Team, Long>{

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Team> findByUser_id(long userId){
        TypedQuery<Team> query = em.createQuery("SELECT e FROM Team e JOIN e.users u WHERE (u.id) = :id ", Team.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    public List<Team> findByManager(long manager){
        TypedQuery<Team> query = em.createQuery("SELECT e FROM Team e WHERE (e.manager.id) = :id", Team.class);
        query.setParameter("id", manager);
        return query.getResultList();
    }
}
