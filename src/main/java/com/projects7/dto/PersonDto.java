package com.projects7.dto;

import com.projects7.model.Person;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class PersonDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2915229440031816522L;

    private UUID id;
    private String username;
    private String role;

    public PersonDto() {
    }

    public PersonDto(Person person) {
        this.setId(person.getId());
        this.setUsername(person.getUsername());
        this.setRole(person.getRole());
    }

}
