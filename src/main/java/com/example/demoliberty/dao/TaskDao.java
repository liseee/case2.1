package com.example.demoliberty.dao;

import com.example.demoliberty.models.Task;
import com.example.demoliberty.models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.example.demoliberty.util.Responses.throwBadRequest;

@RequestScoped
public class TaskDao extends Dao<Task, Long>{

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Task> findByUser_idOrderByDateAsc(long userId){
        TypedQuery<Task> query = em.createQuery("SELECT e FROM Task e JOIN e.users u WHERE (u.id) = :id ORDER BY e.targetDate asc ", Task.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    public List<Task> getPersonalTasks(long userId){
        TypedQuery<Task> query = em.createQuery("SELECT e FROM Task e JOIN e.users u WHERE (u.id) = :id AND e.team IS NULL ORDER BY e.targetDate asc ", Task.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    public List<Task> getTeamTasks(long userId){
        TypedQuery<Task> query = em.createQuery("SELECT e FROM Task e JOIN e.users u WHERE (u.id) = :id AND e.team IS NOT NULL ORDER BY e.targetDate asc ", Task.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }
}
