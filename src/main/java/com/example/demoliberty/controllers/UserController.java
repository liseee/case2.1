package com.example.demoliberty.controllers;

import com.example.demoliberty.models.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/usersBu")
public class UserController {

    private Object User;

    @GET
//    @Produces("text/plain")
    @Produces("application/json")
    public List<User> getUsers() {

        List<User> userList = new ArrayList<>();

//        userList.add(new User("Lise", "lise-jonkman@hotmail.com"));
//        userList.add(new User("Piet", "piet-pietje@hotmail.com"));

        return userList;
    }

}
