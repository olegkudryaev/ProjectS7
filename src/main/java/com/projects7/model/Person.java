package com.projects7.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name="\"person\"")
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4449239897116056084L;
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    @ManyToMany
    @JoinTable(
            name = "person_friends",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Person> friends = new ArrayList<>();
}
