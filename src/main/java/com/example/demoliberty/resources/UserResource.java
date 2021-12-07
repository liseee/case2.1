package com.example.demoliberty.resources;

import com.example.demoliberty.dao.RoleDao;
import com.example.demoliberty.dao.UserDao;
import com.example.demoliberty.filters.Authorized;
import com.example.demoliberty.models.User;
import com.example.demoliberty.util.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Path("users")
public class UserResource {

    @Inject
    private UserDao userDao;

    @Inject
    private RoleDao roleDao;

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;
    /**
     * This method returns the existing/stored events in Json format
     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Transactional
//    public JsonArray getUsers() {
//        JsonObjectBuilder builder = Json.createObjectBuilder();
//        JsonArrayBuilder finalArray = Json.createArrayBuilder();
//        for (User user : userDao.readAllUsers()) {
////            builder.add("firstName", user.getFirstName()).add("lastName", user.getLastName()).add("email", user.getEmail()).add("id", user.getId()).add("role", (JsonObjectBuilder) user.getRole());
//            builder.add("firstName", user.getFirstName()).add("lastName", user.getLastName()).add("email", user.getEmail()).add("id", user.getId()).add("role", user.getRole().getName());
//            finalArray.add(builder.build());
//        }
//        return finalArray.build();
//    }

//    @Authorized
//    @GET
//    public List<User> getAllUsers(){
//        return userDao.readAllUsers();
//    }

    @Authorized
    @GET @Path("{id}")
    public User get(@PathParam("id") Long id) {
        return userDao.getById(id);
    }

    @Authorized
    @Transactional
    @PUT @Path("{id}")
    public User put(@PathParam("id") Long id, User u) {
        u.setId(id);
        return userDao.update(id, u);
    }

//    @Authorized
//    @GET
//    public Collection<User> getAll(@QueryParam("q") String q) {
////        return q == null ? userDao.getAll() : userDao.get(q);
//        return userDao.getAll();
//    }

    @Authorized
    @GET
    public List<User> getAllUsers(@QueryParam("q") String q){
        return q == null ? userDao.getAll() : userDao.get(q);
//        return userDao.readAllUsers();
    }

    // create employee rest api
    @POST
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User createUser(User user) {
        return userDao.add(user);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") long id){
        userDao.remove(id);
    }

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

    @POST
    @Path("/authenticate")
    public User login(User u) {
        try {
            String email = u.getEmail();
            String password = u.getPassword();

            User user = userDao.authenticate(email, password);
            String jwt = issueToken(email);
            user.setToken(jwt);
            u.setPassword("");

            return user;
        } catch (Exception e) {
            throw new NotAuthorizedException("User " + u + " is not authorized.");
        }
    }

    private String issueToken(String email) {
        Key password = keyGenerator.generateKey();
        String jwt = Jwts.builder()
                .setSubject(email)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, password)
                .compact();
        System.out.println("#### generated token: " + jwt);
        return jwt;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

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
