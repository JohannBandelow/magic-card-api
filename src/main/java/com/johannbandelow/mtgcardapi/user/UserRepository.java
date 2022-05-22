package com.johannbandelow.mtgcardapi.user;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(Long userId);

    @Query(value = "SELECT u FROM User u WHERE u.email LIKE :email")
    Optional<User> findUserByEmail(@Param("email") String email);
}
