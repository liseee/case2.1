package com.example.demoliberty.resources;

import com.example.demoliberty.dao.TaskDao;
import com.example.demoliberty.dao.TeamDao;
import com.example.demoliberty.dao.UserDao;
import com.example.demoliberty.filters.Authorized;
import com.example.demoliberty.models.Status;
import com.example.demoliberty.models.Task;
import com.example.demoliberty.models.Team;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
@Path("teams")
public class TeamResource {

    @Inject
    private TeamDao teamDao;

    @Inject
    private TaskDao taskDao;

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    @Authorized
    @GET
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTeams(@PathParam("id") Long id) throws JsonProcessingException {

        List<Team> teams = teamDao.findByManager(id) ;
        final String itemJson = new ObjectMapper().writeValueAsString(teams);
        return itemJson;

    }

    @Authorized
    @GET
    @Path("user/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTeamsFromUser(@PathParam("id") Long id) throws JsonProcessingException {

        List<Team> teams = teamDao.findByUser_id(id) ;
        final String itemJson = new ObjectMapper().writeValueAsString(teams);
        return itemJson;

    }

    @Authorized
    @POST
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String createTeam(Team team) throws JsonProcessingException, ParseException {

        Team newTeam = teamDao.add(team);
        final String itemJson = new ObjectMapper().writeValueAsString(newTeam);
        return itemJson;

    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Authorized
    public void delete(@PathParam("id") long id){

        List<Task> tasks = new ArrayList<>();
        Team team = teamDao.getById(id);

        for (Task task : team.getTasks()){
            taskDao.remove(task.getId());
        }

        team.setTasks(tasks);
        teamDao.remove(id);
    }

    @Authorized
    @GET
    @Path("team/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public String getTask(@PathParam("id") Long id) throws JsonProcessingException {

        Team team = teamDao.getById(id);
        final String itemJson = new ObjectMapper().writeValueAsString(team);
        return itemJson;

    }

    @Authorized
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @PUT @Path("{id}")
    public String put(@PathParam("id") Long id, Team t) throws JsonProcessingException {
        t.setId(id);

        Team team =  teamDao.update(id, t);
        final String itemJson = new ObjectMapper().writeValueAsString(team);
        return itemJson;
    }


}
