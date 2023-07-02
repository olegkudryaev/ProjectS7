package com.projects7.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name="\"person_friends\"")
@AllArgsConstructor
@NoArgsConstructor
public class PersonFriends implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5019239897116056084L;
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "person_id")
    private UUID personId;
    @Column(name = "friend_id")
    private UUID friendId;
}
