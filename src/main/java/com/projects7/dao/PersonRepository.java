package com.projects7.dao;

import com.projects7.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByUsername(String username);
    List<Person> findAll();
    List<Person> findAllByUsername(String userName);
}
