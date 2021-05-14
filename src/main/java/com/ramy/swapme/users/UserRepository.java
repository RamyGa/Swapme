package com.ramy.swapme.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email); //we are going to pass the repository an email (which is the username)
    //then go ahead a find one, its going to be of type Optional User (which will
    //prevent us from having to do null checks etc)
}
