//package com.example.demoliberty;
//
//import com.example.demoliberty.models.Role;
//import com.example.demoliberty.models.User;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.test.api.ArquillianResource;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.core.GenericType;
//import java.net.URL;
//import java.util.List;
//
//import static javax.ws.rs.client.Entity.entity;
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.APPLICATION_XML;
//import static org.junit.Assert.assertEquals;
//import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
//
//@RunWith(Arquillian.class) // 1: dit is een arquillian test
//public class AppIT {
//
//    // Ik maak hier een api-test van: aanroepen van (alle?) endpoints, kijken of die het doen, en of ie teruggeven wat je verwacht.
//
//    @ArquillianResource
//    private URL deploymentURL;
//
//    private String usersResourcePath;
//
//    @Before
//    public void setup() { usersResourcePath = deploymentURL + "api/users"; }
//
//    // 2: creeer een war zodat arq deze kan deployen
//    @Deployment
//    public static Archive<?> createDeployment() {
//        WebArchive warEmpty = ShrinkWrap.create(WebArchive.class, "AppIT.war");
//        WebArchive warFilled = warEmpty
//                .addPackages(true, HelloApplication.class.getPackage())
//                .addAsWebInfResource("META-INF/beans-test.xml", "META-INF/beans.xml") // to activate CDI
//                .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml") // for JPA
//                ;
//
//        return warFilled;
//    }
//
//    @Test // 3: maak testjes
//    @SuppressWarnings("unchecked")
//    public void whenIGetAllContactsIGetTheCorrectResult() {
//        // We gaan hier voor POSTMAN spelen.
//        Client postman = ClientBuilder.newClient().register(JacksonJsonProvider.class);
//
//        Role role = Role.builder().id(1).name("USER").build();
//        User u = User.builder().firstName("Lise").lastName("Jonkman").email("email").password("password").build();
//
////        System.out.println(usersResourcePath);
//        // post some users
//        String contactJson = postman
//                .target(usersResourcePath+"/authenticate/register")
//                .request()
//                .post(entity(u, APPLICATION_JSON), String.class);
//////
////        String contactJson2 = postman
////                .target(contactsResourcePath)
////                .request()
////                .post(entity(u, APPLICATION_JSON), String.class);
////
////        // get all contacts
////        List<User> list = postman
////                .target(contactsResourcePath)
////                .request().get(new GenericType<List<User>>() {});
////
////        assertEquals(list.size(), 2);
////        assertEquals("Lise", list.get(0).getFirstName());
//    }
//}
//
