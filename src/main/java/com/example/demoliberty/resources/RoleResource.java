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

//    @Inject
//    private UserDao userDao;

    @Inject
    private RoleDao roleDao;
    /**
     * This method returns the existing/stored events in Json format
     */
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

//    @DELETE
//    @Path("{id}")
//    @Transactional
//    public void delete(@PathParam("id") long id){
//        userDao.remove(id);
//    }
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)
//    @Transactional
//    public Response add(User input) {
//        JsonObjectBuilder builder = Json.createObjectBuilder();
//
//        try {
//
//            input.setRole(roleDao.findByName("USER"));
//            userDao.add(input);
//            builder.add("firstName", input.getFirstName()).add("lastName", input.getLastName()).add("email", input.getEmail()).add("role", input.getRole().getName());
//
//            return Response
//                    .status(Response.Status.OK)
//                    .entity(builder.build())
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//        }catch (Exception e) {
//            String message = "Something is incorrect" + e;
//            return Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity(message)
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//        }
//    }
//
//    @POST
//    @Path("/authenticate")
////    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response authUser(User input) {
//        JsonObjectBuilder builder = Json.createObjectBuilder();
//        JsonArrayBuilder finalArray = Json.createArrayBuilder();
//
//        try {
//            User loggedUser = userDao.currentUser(input.getEmail());
//
//            if (loggedUser.getPassword().equals(input.getPassword())){
//
//                builder.add("firstName", loggedUser.getFirstName()).add("lastName", loggedUser.getLastName()).add("email", loggedUser.getEmail()).add("id", loggedUser.getId()).add("role", loggedUser.getRole().getName()).add("token", "fake-jwt-token");
////                finalArray.add(builder.build());
//                return Response
//                        .status(Response.Status.OK)
//                        .entity(builder.build())
//                        .type(MediaType.APPLICATION_JSON)
//                        .build();
//
//            }else {
//                throw new Exception();
//            }
//
//        } catch (Exception e) {
//            String message = "Username or password is incorrect";
//            return Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity(message)
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//
//        }
//
//    }

//    @POST
//    @Path("/authenticatee")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response authUserr(User input) {
//
//        try {
//            User loggedUser = userDao.currentUser(input.getEmail());
//
//            if (loggedUser.getPassword().equals(input.getPassword())){
//
//                String message = "{\"email\":\""+loggedUser.getEmail()+"\",\"role\":\""+loggedUser.getRole().getName()+"\",\"token\":\"fake-jwt-token\"}";
//                return Response
//                        .status(Response.Status.OK)
//                        .entity(message)
//                        .type(MediaType.APPLICATION_JSON)
//                        .build();
//
//            }else {
//                throw new Exception();
//            }
//
//        } catch (Exception e) {
//            String message = "Username or password is incorrect";
//            return Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity(message)
//                    .build();
//        }
//
//    }
//
//    @POST
//    @Path("/authenticatetest")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public JsonArray authUserTets(User input) {
//        JsonObjectBuilder builder = Json.createObjectBuilder();
//        JsonArrayBuilder finalArray = Json.createArrayBuilder();
//
//        try {
//            User loggedUser = userDao.currentUser(input.getEmail());
//
//            if (loggedUser.getPassword().equals(input.getPassword())){
//
//                builder.add("firstName", loggedUser.getFirstName()).add("lastName", loggedUser.getLastName()).add("email", loggedUser.getEmail()).add("id", loggedUser.getId()).add("role", loggedUser.getRole().getName()).add("token", "fake-jwt-token");
//                finalArray.add(builder.build());
//                return finalArray.build();
//
//            }else {
//                throw new Exception();
//            }
//
//        } catch (Exception e) {
//            builder.add("email", input.getEmail()).add("password", input.getPassword());
//            finalArray.add(builder.build());
//            return finalArray.build();
//
////                    System.out.println("Ongeldige combinatie van username en password, probeer het opnieuw");
////                System.out.println("\nOngeldige combinatie van username en password");
//        }
//
//
//    }

//    @POST
//    @Path("/authenticate")
////    @Produces(HttpResponse)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public JsonArray authUser(User input) {
//
//        try {
//            User loggedUser = userDao.currentUser(input.getEmail());
//
//            if (loggedUser.getPassword().equals(input.getPassword())){
//
//            }else {
//                throw new Exception();
//            }
//        } catch (Exception e) {
//            System.out.println("Ongeldige combinatie van username en password, probeer het opnieuw");
////                System.out.println("\nOngeldige combinatie van username en password");
//        }
//
//        return of
//    }
}
