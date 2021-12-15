package com.example.demoliberty.resources;

import com.example.demoliberty.dao.TaskDao;
import com.example.demoliberty.dao.TeamDao;
import com.example.demoliberty.dao.UserDao;
import com.example.demoliberty.filters.Authorized;
import com.example.demoliberty.models.Status;
import com.example.demoliberty.models.Task;
import com.example.demoliberty.models.Team;
import com.example.demoliberty.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestScoped
@Path("tasks")
public class TaskResource {

    @Inject
    private TaskDao taskDao;

    @Inject
    private UserDao userDao;

    @Inject
    private TeamDao teamDao;

    @Context
    private UriInfo uriInfo;

    @Authorized
    @GET
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTasks(@PathParam("id") Long id, @QueryParam("q") String q) throws JsonProcessingException {
        List<Task> tasks = new ArrayList<>();

        if (q != null){
            switch (q){
                case "1":
                    System.out.println("1");
                    tasks = taskDao.findByUser_idOrderByDateAsc(id);
                    break;
                case "2":
                    System.out.println("2");
                    tasks = taskDao.getPersonalTasks(id);
                    break;
                case "3":
                    System.out.println("3");
                    tasks = taskDao.getTeamTasks(id);
                    break;
            }
        } else {
            tasks = taskDao.findByUser_idOrderByDateAsc(id);
        }

        final String itemJson = new ObjectMapper().writeValueAsString(tasks);
        return itemJson;

    }

    @GET
    @Path("test/{id}")
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTasksList(@PathParam("id") Long id, @QueryParam("q") String q) throws IOException {

        List<Task> tasks = taskDao.findByUser_idOrderByDateAsc(id);
        final String itemJson = new ObjectMapper().writeValueAsString(tasks);
        return itemJson;

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

    }

    @Authorized
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @PUT @Path("{id}")
    public String put(@PathParam("id") Long id, Task t) throws JsonProcessingException, ParseException {
        t.setId(id);

        Task oldTask = taskDao.getById(id);

        List<User> newUsers = new ArrayList<>();
        for (User user : t.getUsers()){
            User u = userDao.getById(user.getId());
            newUsers.add(u);
        }
        t.setTeam(oldTask.getTeam());
        t.setUsers(newUsers);

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date targetDate = t.getTargetDate();
        Date todayWithZeroTime = formatter.parse(formatter.format(targetDate));
        t.setTargetDate(todayWithZeroTime);

        Task newTask =  taskDao.update(id, t);
        final String itemJson = new ObjectMapper().writeValueAsString(newTask);
        return itemJson;
    }

    @Authorized
    @POST
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String createTask(Task task) throws JsonProcessingException, ParseException {

        List<User> newUsers = new ArrayList<>();
        for (User user : task.getUsers()){
            User u = userDao.getById(user.getId());
            newUsers.add(u);
        }

        Team teamteset = task.getTeam();
        Long teamId = teamteset.getId();

        if (teamId.equals(0)){
            task.setTeam(null);
        } else {
            task.setTeam(teamDao.getById(task.getTeam().getId()));
        }

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = sdf.parse(String.valueOf(task.getTargetDate()));
//        task.setTargetDate(date);
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date targetDate = task.getTargetDate();
        Date todayWithZeroTime = formatter.parse(formatter.format(targetDate));
        task.setTargetDate(todayWithZeroTime);

        task.setStatus(Status.TODO);

        Task newTask =  taskDao.add(task);
        final String itemJson = new ObjectMapper().writeValueAsString(newTask);
        return itemJson;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Authorized
    public void delete(@PathParam("id") long id){
        taskDao.remove(id);
    }

}
