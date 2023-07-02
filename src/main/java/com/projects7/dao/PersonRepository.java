package com.projects7.dao;

import com.projects7.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByUsername(String username);
    List<Person> findAll();
    @Query("select p from Person as p "
            + "where lower(p.username) like lower(concat('%', ?1, '%')) "
    )
    List<Person> findAllByUsername(String username);
}
