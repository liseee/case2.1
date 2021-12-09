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

//    @PersistenceContext(name = "jpa-unit")
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

//    public void createEvent(User user) {
//        em.persist(user);
//    }
//
//    public User readUser(int eventId) {
//        return em.find(User.class, eventId);
//    }
//
//    public void updateUser(User user) {
//        em.merge(user);
//    }
//
//    public void deleteUser(User user) {
//        em.remove(user);
//    }

//    public List<User> readAllUsers() {
//        return em.createNamedQuery("User.findAll", User.class).getResultList();
//    }
//
//    public List<User> findUser(long id, String name, String email) {
//        return em.createNamedQuery("User.findUser", User.class)
//                .setParameter("id", id)
//                .setParameter("name", name)
//                .setParameter("email", email).getResultList();
//    }
//
//    public User currentUser(String email) {
//        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE UPPER (u.email) = :email", User.class);
//        query.setParameter("email", email);
//        return query.getSingleResult();
//    }
//
////    @TransactionAttribute(REQUIRED)  // = default; whole annotation can be omitted when choosing REQUIRED.
//    //                                  Deze methode wordt in een databasetransactie op de server uitgevoerd.
//    //                                  Als er al een transactie loopt, gebruikt de server die, anders maakt hij een nieuwe transactie aan.
////    @TransactionAttribute(REQUIRED)
//    public User add(User c) {
////        em.getTransaction().begin();
//        em.persist(c);
////        em.getTransaction().commit();
//        return c;
//    }
//
//    public void remove(Long id) {
////        if (getUser(id).isPresent()){
////            em.remove(getUser(id));
////        }else {
////            throwBadRequest(id);
////        }
////
//       try {
//           getUser(id).ifPresent(em::remove);
//       } catch (Exception e){
//           throwBadRequest(id);
//       }
//    }
//
//    public Optional<User> getUser(Long id) {
//        return Optional.ofNullable(em.find(User.class, id));
//    }
//
//    // login authentication
//    public User authenticate(String email, String password) {
////        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
////        query.setParameter("login", login);
////        query.setParameter("password", PasswordUtils.digestPassword(password));
//
//        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE UPPER (u.email) = :email", User.class);
//        query.setParameter("email", email);
//        User user = query.getSingleResult();
//
//        if (!password.equals(user.getPassword()))throw new SecurityException("Invalid user/password");
//        if (user == null) throw new SecurityException("Invalid user/password");
//
//        return user;
//    }
}
