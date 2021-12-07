package com.example.demoliberty.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@Setter @NoArgsConstructor @AllArgsConstructor
@NamedQuery(name = "Role.findAll", query = "SELECT e FROM Role e")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

}
