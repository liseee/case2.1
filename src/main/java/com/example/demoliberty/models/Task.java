package com.example.demoliberty.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity
@NamedQuery(name = "Task.findAll", query = "SELECT e FROM Task e")
@Table(name = "Task")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@DynamicUpdate
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "NAME")
    private String taskName;

    @Column(name = "DESCRIPTION")
    private String taskDesc;

    @Temporal(TemporalType.DATE)
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date targetDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @JsonbTransient
    @JoinColumn(name="team_id")
    //@JsonManagedReference
    @JsonIgnoreProperties(value = {"user.id", "tasks", "teamDesc", "manager" })
    private Team team;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tasks_users",
            joinColumns = @JoinColumn(
                    name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    @JsonIgnoreProperties(value = {"token", "email", "password", "role" })
    private List<User> users;
}


