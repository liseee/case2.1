package com.example.demoliberty.dao;

import com.example.demoliberty.models.User;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
@Startup
public class UserDao extends Dao<User, Long>{

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public User currentUser(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE UPPER (u.email) = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    // login authentication
    public User authenticate(String email, String password) {

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE UPPER (u.email) = :email", User.class);
        query.setParameter("email", email);
        User user = query.getSingleResult();

        if (!password.equals(user.getPassword()))throw new SecurityException("Invalid user/password");
        if (user == null) throw new SecurityException("Invalid user/password");

        return user;
    }

    public boolean findByEmail(String userEmail){
        TypedQuery<User> findByEmail = em.createQuery("SELECT u FROM User u WHERE UPPER(u.email) = :email", User.class);
        findByEmail.setParameter("email", userEmail);
        return findByEmail.getResultList().size() == 1;
    }

    public List<User> getAllExcept(long userId){
        TypedQuery<User> query = em.createQuery("SELECT e FROM User e WHERE (e.id) <> :id", User.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }
}
