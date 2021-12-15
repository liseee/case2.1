package com.example.demoliberty;

import com.example.demoliberty.models.Role;
import com.example.demoliberty.models.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.net.URL;
import java.util.List;

import static com.example.demoliberty.Util.pomDependency;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class HelloApplicationIT {

    @ArquillianResource
    private URL deploymentURL;

    private String resourcePath;

    @Before
    public void setup() {
        resourcePath = deploymentURL + "api/";
    }

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "HelloApplicationIT.war")
                .addPackages(true, HelloApplication.class.getPackage())
                .addAsWebInfResource("META-INF/beans-test.xml", "META-INF/beans.xml")
                .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
                .addAsLibraries(pomDependency("io.jsonwebtoken", "jjwt"))
                .addAsLibraries(pomDependency("commons-codec", "commons-codec"))
                ;

        System.out.println(archive.toString(true));
        return archive;
    }


    @Test
    public void whenContactIsPostedICanGetIt() {

        System.out.println("####################################################################### resourcePath=" + resourcePath);

        Client http = ClientBuilder.newClient();

        // add role:
        Role r = Role.builder().id(1).name("USER").build();
        String postedRole = http
                .target(resourcePath + "roles")
                .request().post(entity(r, APPLICATION_JSON), String.class);

        User u = User.builder().id(1).firstName("Lise").lastName("Jonkman").email("email").password("password").build();

        // register:
        String postedContact = http
                .target(resourcePath + "users/authenticate/register")
                .request().post(entity(u, APPLICATION_JSON), String.class);

        System.out.println(postedContact);


        // get all contacts
        List<User> list = http
                .target(resourcePath + "users")
                .request().get(new GenericType<List<User>>() {});



//        String allContacts = http
//                .target(resourcePath+"/authenticate/register")
//                .request().get(String.class);
//
//        System.out.println(allContacts);
        System.out.println(list);

        assertEquals(list.size(), 1);
        assertEquals("Lise", list.get(0).getFirstName());

//        assertThat(allContacts, containsString("\"id\":\"1\""));
//        assertThat(allContacts, containsString("\"firstName\":\"Lise\""));
//        assertThat(allContacts, containsString("\"lastName\":\"Jonkman\""));
//        assertThat(allContacts, containsString("\"email\":\"email\""));
    }

}
