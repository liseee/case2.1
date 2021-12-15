package com.example.demoliberty.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@XmlRootElement
@Entity
@Table(name = "User")
@NamedQuery(name = "User.findAll", query = "SELECT e FROM User e")
@NamedQuery(name = "User.findUser", query = "SELECT e FROM User e WHERE "
        + "e.firstName = :firstName AND e.email = :email AND e.id = :id")
@NamedQuery(
        name = "User.search",
        query = "select c from User c " +
                "INNER JOIN Role r ON c.role=r " +
                "where c.firstName LIKE :q " +
                "OR c.lastName LIKE :q " +
                "OR c.email LIKE :q " +
                "OR r.name LIKE :q")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    private String token;

    @ManyToOne
    @JoinColumn(name="ROLE_ID")
    private Role role;

    public User(String firstName, String lastName, String email, String password, Role user) {
    }

    public User(String firstName, String lastName, String email, String password) {
    }

}
