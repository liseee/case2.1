package com.example.demoliberty.resources;

import com.example.demoliberty.dao.RoleDao;
import com.example.demoliberty.dao.TaskDao;
import com.example.demoliberty.dao.UserDao;
import com.example.demoliberty.filters.Authorized;
import com.example.demoliberty.models.Task;
import com.example.demoliberty.models.User;
import com.example.demoliberty.util.KeyGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@RequestScoped
@Path("tasks")
public class TaskResource {

    @Inject
    private TaskDao taskDao;

    @Inject
    private UserDao userDao;

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

//    @Authorized
//    @GET @Path("{id}")
//    public Task get(@PathParam("id") Long id) {
//        return taskDao.getById(id);
//    }
//
//    @Authorized
//    @Transactional
//    @PUT @Path("{id}")
//    public Task put(@PathParam("id") Long id, Task t) {
//        t.setId(id);
//        return taskDao.update(id, t);
//    }

//    @Authorized
//    @Transactional
//    @GET
//    @Path("{id}")
//    public List<Task> getAllTasks(@PathParam("id") Long id, @QueryParam("q") String q){
//        return q == null ? taskDao.getAllByUser_id(id) : taskDao.get(q);
////        return q == null ? taskDao.getAll() : taskDao.get(q);
////        return userDao.readAllUsers();
//    }

//    @Authorized
//    @Transactional
//    @GET
//    @Path("{id}")
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Task> findTasks(@PathParam("id") Long id){
////        return taskDao.findByUser_idOrderByDateAsc(id);
//        return taskDao.getAll();
////        return q == null ? taskDao.getAll() : taskDao.get(q);
////        return userDao.readAllUsers();
//    }

    @Authorized
    @GET
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTasks(@PathParam("id") Long id, @QueryParam("q") String q) throws JsonProcessingException {

        List<Task> tasks = q == null ? taskDao.findByUser_idOrderByDateAsc(id) : taskDao.get(q);
        final String itemJson = new ObjectMapper().writeValueAsString(tasks);
        return itemJson;

//        return userDao.readAllUsers();
    }

    @Authorized
    @GET
    @Path("task/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getTask(@PathParam("id") Long id) throws JsonProcessingException {

       Task tasks = taskDao.getById(id);
        final String itemJson = new ObjectMapper().writeValueAsString(tasks);
        return itemJson;

//        return userDao.readAllUsers();
    }

    @Authorized
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @PUT @Path("{id}")
    public String put(@PathParam("id") Long id, Task t) throws JsonProcessingException {
        t.setId(id);

        Task oldTask = taskDao.getById(id);

        List<User> newUsers = new ArrayList<>();
        for (User user : t.getUsers()){
            User u = userDao.getById(user.getId());
            newUsers.add(u);
        }
        t.setTeam(oldTask.getTeam());
        t.setUsers(newUsers);


        Task newTask =  taskDao.update(id, t);
        final String itemJson = new ObjectMapper().writeValueAsString(newTask);
        return itemJson;
    }

//    @Authorized
//    @Transactional
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @PUT @Path("{id}")
//    public Task put(@PathParam("id") Long id, Task t) {
//        t.setId(id);
//
//        Task oldTask = taskDao.getById(id);
//
//        List<User> newUsers = new ArrayList<>();
//        for (User user : t.getUsers()){
//            User u = userDao.getById(user.getId());
//            newUsers.add(u);
//        }
//        t.setTeam(oldTask.getTeam());
//        t.setUsers(newUsers);
//
////        return taskDao.update(id, t);
//
//        return oldTask;
//    }

//    // create employee rest api
//    @POST
//    @Transactional
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Task createUser(Task task) {
//        return taskDao.add(task);
//    }
//
//    @DELETE
//    @Path("{id}")
//    @Transactional
//    public void delete(@PathParam("id") long id){
//        taskDao.remove(id);
//    }


}
