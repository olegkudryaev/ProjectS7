package com.projects7.dao;

import com.projects7.model.PersonFriends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonFriendsRepository extends JpaRepository<PersonFriends, UUID> {
}
