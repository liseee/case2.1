package com.example.demoliberty.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity
@Table(name = "Team")
@NamedQuery(name = "Team.findAll", query = "SELECT e FROM Team e")
// Mocht dit problemen opleveren weer toevoegen, weggehaald voor task tabel
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "NAME")
    private String teamName;

    @Column(name = "DESCRIPTION")
    private String teamDesc;

    @ManyToOne
    //@JsonManagedReference
    @JoinColumn(name="MANAGER_ID", nullable = false)
    private User manager;

    @OneToMany(mappedBy="team", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    //@JsonBackReference
//    @JsonbTransient
    private List<Task> tasks;

    //    @ManyToMany(mappedBy = "teams")
//    List<User> users;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
   // @JsonIgnore
    @JsonIgnoreProperties(value = {"email", "password", "token", "role" })
    @JoinTable(
            name = "teams_users",
            joinColumns = @JoinColumn(
                    name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<User> users;
}
