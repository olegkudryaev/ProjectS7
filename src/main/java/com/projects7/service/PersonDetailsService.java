package com.projects7.service;

import com.projects7.dao.PersonFriendsRepository;
import com.projects7.dao.PersonRepository;
import com.projects7.dto.PersonDto;
import com.projects7.dto.PersonDtoInput;
import com.projects7.dto.wrapped.DtoWrapper;
import com.projects7.dto.wrapped.PersonDtoWrapped;
import com.projects7.model.Person;
import com.projects7.security.PersonDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService{
    private final PersonRepository personRepository;
    private final String secretKey = "eyJhbGciOiJIUzI1NiJ9ew0KICAic3ViIjogIjEyMzQ1Njc4OTAiLA0KICAibmFtZSI6ICJBbmlzaCBOYXRoIiwNCiAgImlhdCI6IDE1MTYyMzkwMjINCn0olSvwgv4f72vJY4HIcZuYe7kQvzFtFi5lDbmiX2sXGOQ";

    public PersonDtoWrapped getUsers() {
        List<Person> allPerson = personRepository.findAll();
        List<PersonDto> allPersonDto = new ArrayList<>();
        allPerson.forEach(person -> allPersonDto.add(new PersonDto(person)));
        return new PersonDtoWrapped(allPersonDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var person = personRepository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });
        return PersonDetails.builder().person(person).build();
    }

    public String authorization(PersonDtoInput personDtoInput) {
        Person person = personRepository.findByUsername(personDtoInput.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });
        if (person.getPassword().equals(personDtoInput.getPassword())){
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
    
    public PersonDtoWrapped findPersonByUsername(String token, String userName) throws Exception {
        try {
            if (validateToken(token) == null) {
                return new PersonDtoWrapped(null);
            }
            List<Person> personList = personRepository.findAllByUsername(userName);
            List<PersonDto> personDtoList = new ArrayList<>();
            personList.forEach(p -> personDtoList.add(new PersonDto(p)));
            return new PersonDtoWrapped(personDtoList);
        }catch (Exception e){
            throw e;
        }
    }



    public String validateToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

             if (claims.getExpiration().before(new Date())) {
             return null;
        }
        return claims.get("sub", String.class);
        }catch (Exception e){
            throw new Exception("Token is not valid");
        }
    }

    public DtoWrapper addFriend(String token, String userId) throws Exception {
        String idPerson = validateToken(token);
        if(validateToken(token)==null){
            return DtoWrapper.builder()
                    .message("токен не действителен")
                    .success(false)
                    .snackbarType("Info")
                    .build();
        }
        Optional<Person> friend = personRepository.findById(UUID.fromString(userId));
        Optional<Person> person = personRepository.findById(UUID.fromString(idPerson));
        if(friend.isPresent()&&person.isPresent()){
            List<Person> friends = person.get().getFriends();
            friends.add(friend.get());
            person.get().setFriends(friends);
            try {
                personRepository.save(person.get());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return DtoWrapper.builder()
                .message("Друг добавлен")
                .success(true)
                .snackbarType("Info")
                .build();
    }

    public DtoWrapper deleteFriend(String token, String userId) throws Exception {
        String idPerson = validateToken(token);
        if(validateToken(token)==null){
            return DtoWrapper.builder()
                    .message("токен не действителен")
                    .success(false)
                    .snackbarType("Info")
                    .build();
        }
        Optional<Person> friend = personRepository.findById(UUID.fromString(userId));
        Optional<Person> person = personRepository.findById(UUID.fromString(idPerson));
        if(friend.isPresent()&&person.isPresent()){
            List<Person> friends = person.get().getFriends();
            for (int i = 0; i < friends.size(); i++) {
                if(friend.get().getId().equals(friends.get(i).getId())){
                    friends.remove(i);
                    break;
                }
            }
            person.get().setFriends(friends);
            try {
                personRepository.save(person.get());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return DtoWrapper.builder()
                .message("Друг добавлен")
                .success(true)
                .snackbarType("Info")
                .build();
    }

    public PersonDtoWrapped getAllFriends(String token) throws Exception {
        String idPerson = validateToken(token);
        if(validateToken(token)==null){
            return new PersonDtoWrapped(null);
        }
        Optional<Person> personOpt = personRepository.findById(UUID.fromString(idPerson));
        List<PersonDto> personDtoList = new ArrayList<>();
        List<Person> personList = personOpt.get().getFriends();
        personList.forEach(person -> personDtoList.add(new PersonDto(person)));
        return new PersonDtoWrapped(personDtoList);
    }
}
