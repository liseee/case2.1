package com.example.demoliberty.resources;

import com.example.demoliberty.dao.RoleDao;
import com.example.demoliberty.dao.UserDao;
import com.example.demoliberty.models.Role;
import com.example.demoliberty.models.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("roles")
public class RoleResource {

    @Inject
    private RoleDao roleDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getRoles() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (Role role : roleDao.readAllRoles()) {
            builder.add("id", role.getId()).add("name", role.getName());
            finalArray.add(builder.build());
        }
        return finalArray.build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Role addRole(Role r) {
        return roleDao.add(r);
    }

}
