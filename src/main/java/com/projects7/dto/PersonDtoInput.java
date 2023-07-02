package com.projects7.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PersonDtoInput implements Serializable {
    @Serial
    private static final long serialVersionUID = 695847623907984912L;
    private String username;
    private String password;
}
