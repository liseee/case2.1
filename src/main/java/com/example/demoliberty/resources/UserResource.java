package com.example.demoliberty.resources;

import com.example.demoliberty.dao.RoleDao;
import com.example.demoliberty.dao.UserDao;
import com.example.demoliberty.filters.Authorized;
import com.example.demoliberty.models.User;
import com.example.demoliberty.util.KeyGenerator;
import com.example.demoliberty.util.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Inject
    private PasswordEncoder passwordEncoder;

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
    @GET
    public List<User> getAllUsers(@QueryParam("q") String q){
        return q == null ? userDao.getAll() : userDao.get(q);
    }

    @Authorized
    @GET
    @Path("/manager/{id}")
    public List<User> getAllUsersManager(@PathParam("id") long id){
        return userDao.getAllExcept(id);
    }

    @POST
    @Transactional
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(APPLICATION_JSON)
    public User createUser(User user) {
        return userDao.add(user);
    }

    @Authorized
    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") long id){
        userDao.remove(id);
    }

    @POST
    @Path("/authenticate/register")
    @Transactional
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public User register(User u) {

        u.setRole(roleDao.findByName("USER"));
        u.setPassword(passwordEncoder.encrypt(u.getPassword()));

        if (userDao.findByEmail(u.getEmail())){
            throw new NotAuthorizedException("Email van " + u + " bestaat al.");
        } else {
            return userDao.add(u);
        }
    }

    @POST
    @Path("/authenticate")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public User login(User u) {
        try {
            String email = u.getEmail();
            String password = passwordEncoder.encrypt(u.getPassword());

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

}
