package com.projects7.service;

import com.projects7.dao.PersonFriendsRepository;
import com.projects7.dao.PersonRepository;
import com.projects7.dto.PersonDtoInput;
import com.projects7.dto.wrapped.DtoWrapper;
import com.projects7.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public DtoWrapper createUser(PersonDtoInput personDtoInput) {
        var encode = passwordEncoder.encode(personDtoInput.getPassword());
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setUsername(personDtoInput.getUsername());
        person.setPassword(encode);
        person.setRole("ROLE_USER");
        personRepository.save(person);
        return DtoWrapper.builder()
                .message("Пользователь зарегестрирован")
                .success(true)
                .snackbarType("Info")
                .build();
    }

}
