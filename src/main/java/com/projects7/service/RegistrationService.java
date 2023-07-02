package com.projects7.service;

import com.projects7.dao.PersonFriendsRepository;
import com.projects7.dao.PersonRepository;
import com.projects7.dto.PersonDtoInput;
import com.projects7.dto.wrapped.DtoWrapper;
import com.projects7.model.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    private final String secretKey = "eyJhbGciOiJIUzI1NiJ9ew0KICAic3ViIjogIjEyMzQ1Njc4OTAiLA0KICAibmFtZSI6ICJBbmlzaCBOYXRoIiwNCiAgImlhdCI6IDE1MTYyMzkwMjINCn0olSvwgv4f72vJY4HIcZuYe7kQvzFtFi5lDbmiX2sXGOQ";

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

    public String authorization(PersonDtoInput personDtoInput) {
        Person person = personRepository.findByUsername(personDtoInput.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });
        if (passwordEncoder.matches(personDtoInput.getPassword(), person.getPassword())){
            getToken(person.getId());
            return getToken(person.getId());
        }
        return null;
    }

    private String getToken(UUID id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);
        String token = Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

}
